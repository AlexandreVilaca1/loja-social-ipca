import {onCall, HttpsError} from "firebase-functions/v2/https";
import * as logger from "firebase-functions/logger";
import * as admin from "firebase-admin";
import {Connector} from "@google-cloud/cloud-sql-connector";

// Initialize Firebase Admin
admin.initializeApp();

// Cloud SQL Connector instance
let connector: Connector | null = null;

/**
 * Get PostgreSQL connection using Cloud SQL Connector
 */
async function getConnection() {
  if (!connector) {
    connector = new Connector();
  }

  const clientOpts = await connector.getOptions({
    instanceConnectionName: "sas-ipca:europe-west1:sas-ipca-instance2",
  });

  const {Pool} = require("pg");
  const pool = new Pool({
    ...clientOpts,
    user: "postgres",
    password: process.env.POSTGRES_PASSWORD,
    database: "fdcdb",
    max: 5,
  });

  return pool;
}

// ============================================================================
// MANAGE DISTRIBUTION ITEM - Função única que gerencia TUDO
// ============================================================================

interface ManageDistributionItemRequest {
  action: "CREATE" | "DELETE" | "UPDATE";
  distributionId: string;
  lotId: string;
  quantity?: number; // Required for CREATE and UPDATE
  newQuantity?: number; // Required for UPDATE (new quantity)
  observations?: string;
}

interface ManageDistributionItemResponse {
  success: boolean;
  message: string;
  stockAdjustment?: {
    lotId: string;
    lotCode: string;
    previousQuantity: number;
    newQuantity: number;
    adjustment: number;
  };
}

/**
 * Cloud Function para gerenciar itens de distribuição
 *
 * AÇÕES:
 * - CREATE: Adiciona item à distribuição e RETIRA do stock
 * - DELETE: Remove item da distribuição e DEVOLVE ao stock
 * - UPDATE: Atualiza quantidade e AJUSTA o stock (devolve ou retira diferença)
 *
 * REGRAS DE NEGÓCIO:
 * 1. Stock é sempre atualizado atomicamente com a operação
 * 2. Valida stock disponível antes de qualquer operação
 * 3. Todas as operações em transação (COMMIT ou ROLLBACK)
 * 4. Não permite stock negativo
 */
export const manageDistributionItem = onCall<ManageDistributionItemRequest>(
  {
    region: "europe-west1",
    cors: true,
    timeoutSeconds: 60,
    memory: "256MiB",
    secrets: ["POSTGRES_PASSWORD"],
  },
  async (request): Promise<ManageDistributionItemResponse> => {
    const {data, auth} = request;

    logger.info("=== manageDistributionItem called ===", {
      hasAuth: !!auth,
      authUid: auth?.uid,
      action: data.action,
      distributionId: data.distributionId,
      lotId: data.lotId,
    });

    // 1. Authentication check
    if (!auth) {
      throw new HttpsError("unauthenticated", "User must be authenticated");
    }

    const {action, distributionId, lotId, quantity, newQuantity, observations} = data;

    // 2. Validate required fields
    if (!action || !distributionId || !lotId) {
      throw new HttpsError(
        "invalid-argument",
        "action, distributionId, and lotId are required"
      );
    }

    if ((action === "CREATE" || action === "UPDATE") && (!quantity || quantity <= 0)) {
      throw new HttpsError(
        "invalid-argument",
        "quantity must be a positive number for CREATE/UPDATE actions"
      );
    }

    if (action === "UPDATE" && (!newQuantity || newQuantity < 0)) {
      throw new HttpsError(
        "invalid-argument",
        "newQuantity is required for UPDATE action"
      );
    }

    let pool;
    let client;

    try {
      pool = await getConnection();
      client = await pool.connect();

      // Start transaction
      await client.query("BEGIN");
      logger.info("Transaction started");

      // Get lot information
      const lotResult = await client.query(
        `SELECT id, lot_code, current_quantity FROM lot WHERE id = $1`,
        [lotId]
      );

      if (lotResult.rows.length === 0) {
        await client.query("ROLLBACK");
        throw new HttpsError("not-found", "Lot not found");
      }

      const lot = lotResult.rows[0];
      const previousQuantity = lot.current_quantity;
      let newStockQuantity = previousQuantity;
      let adjustment = 0;

      // Execute action based on type
      switch (action) {
        case "CREATE": {
          // TypeScript assertion - já validamos que quantity existe
          const qty = quantity!;
          logger.info(`Creating distribution item: ${qty} units`);

          // Validate stock availability
          if (lot.current_quantity < qty) {
            await client.query("ROLLBACK");
            throw new HttpsError(
              "failed-precondition",
              `Insufficient stock in lot ${lot.lot_code}. ` +
              `Available: ${lot.current_quantity}, Required: ${qty}`
            );
          }

          // Create distribution item
          await client.query(
            `INSERT INTO distribution_item (distribution_id, lot_id, quantity, observations, created_at)
             VALUES ($1, $2, $3, $4, CURRENT_TIMESTAMP)`,
            [distributionId, lotId, qty, observations || null]
          );

          // Update stock (subtract)
          newStockQuantity = lot.current_quantity - qty;
          adjustment = -qty;

          await client.query(
            `UPDATE lot SET current_quantity = $1, updated_at = CURRENT_TIMESTAMP WHERE id = $2`,
            [newStockQuantity, lotId]
          );

          logger.info(
            `Item created. Stock adjusted: ${lot.current_quantity} -> ${newStockQuantity}`
          );
          break;
        }

        case "DELETE": {
          logger.info("Deleting distribution item");

          // Get current item quantity before deleting
          const itemResult = await client.query(
            `SELECT quantity FROM distribution_item
             WHERE distribution_id = $1 AND lot_id = $2`,
            [distributionId, lotId]
          );

          if (itemResult.rows.length === 0) {
            await client.query("ROLLBACK");
            throw new HttpsError("not-found", "Distribution item not found");
          }

          const itemQuantity = itemResult.rows[0].quantity;

          // Delete distribution item
          await client.query(
            `DELETE FROM distribution_item
             WHERE distribution_id = $1 AND lot_id = $2`,
            [distributionId, lotId]
          );

          // Return to stock (add back)
          newStockQuantity = lot.current_quantity + itemQuantity;
          adjustment = itemQuantity;

          await client.query(
            `UPDATE lot SET current_quantity = $1, updated_at = CURRENT_TIMESTAMP WHERE id = $2`,
            [newStockQuantity, lotId]
          );

          logger.info(
            `Item deleted. Stock restored: ${lot.current_quantity} -> ${newStockQuantity}`
          );
          break;
        }

        case "UPDATE": {
          // TypeScript assertions - já validamos que existem
          const oldQty = quantity!;
          const newQty = newQuantity!;

          logger.info(`Updating distribution item: ${oldQty} -> ${newQty}`);

          // Get current item quantity
          const itemResult = await client.query(
            `SELECT quantity FROM distribution_item
             WHERE distribution_id = $1 AND lot_id = $2`,
            [distributionId, lotId]
          );

          if (itemResult.rows.length === 0) {
            await client.query("ROLLBACK");
            throw new HttpsError("not-found", "Distribution item not found");
          }

          const oldQuantity = itemResult.rows[0].quantity;
          const difference = newQty - oldQuantity;

          if (difference > 0) {
            // Need MORE stock (subtracting)
            if (lot.current_quantity < difference) {
              await client.query("ROLLBACK");
              throw new HttpsError(
                "failed-precondition",
                `Insufficient stock in lot ${lot.lot_code}. ` +
                `Available: ${lot.current_quantity}, Additional needed: ${difference}`
              );
            }
            newStockQuantity = lot.current_quantity - difference;
            adjustment = -difference;
          } else if (difference < 0) {
            // Returning stock (adding back)
            newStockQuantity = lot.current_quantity + Math.abs(difference);
            adjustment = Math.abs(difference);
          }
          // If difference === 0, no stock change needed

          // Update distribution item
          await client.query(
            `UPDATE distribution_item
             SET quantity = $1, observations = $2
             WHERE distribution_id = $3 AND lot_id = $4`,
            [newQty, observations || null, distributionId, lotId]
          );

          // Update stock if there was a difference
          if (difference !== 0) {
            await client.query(
              `UPDATE lot SET current_quantity = $1, updated_at = CURRENT_TIMESTAMP WHERE id = $2`,
              [newStockQuantity, lotId]
            );
          }

          logger.info(
            `Item updated. Stock adjusted: ${lot.current_quantity} -> ${newStockQuantity} (${adjustment >= 0 ? "+" : ""}${adjustment})`
          );
          break;
        }

        default:
          await client.query("ROLLBACK");
          throw new HttpsError("invalid-argument", `Invalid action: ${action}`);
      }

      // Commit transaction
      await client.query("COMMIT");
      logger.info("Transaction committed successfully");

      return {
        success: true,
        message: `Distribution item ${action.toLowerCase()}d successfully`,
        stockAdjustment: {
          lotId: lot.id,
          lotCode: lot.lot_code,
          previousQuantity: previousQuantity,
          newQuantity: newStockQuantity,
          adjustment: adjustment,
        },
      };
    } catch (error: any) {
      // Rollback on any error
      if (client) {
        try {
          await client.query("ROLLBACK");
          logger.info("Transaction rolled back");
        } catch (rollbackError) {
          logger.error("Error during rollback:", rollbackError);
        }
      }

      logger.error("Error managing distribution item:", error);

      // Re-throw HttpsError
      if (error instanceof HttpsError) {
        throw error;
      }

      throw new HttpsError(
        "internal",
        error.message || "Failed to manage distribution item"
      );
    } finally {
      // Release client back to pool
      if (client) {
        try {
          client.release();
        } catch (releaseError) {
          logger.error("Error releasing client:", releaseError);
        }
      }
    }
  }
);

