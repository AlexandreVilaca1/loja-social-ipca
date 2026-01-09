import {onCall, HttpsError} from "firebase-functions/v2/https";
import {onSchedule} from "firebase-functions/v2/scheduler";
import * as logger from "firebase-functions/logger";
import * as admin from "firebase-admin";
import {Connector} from "@google-cloud/cloud-sql-connector";

// Initialize Firebase Admin
admin.initializeApp();

// ============================================================================
// EMAIL CONFIGURATION - CENTRALIZED AND SECURE
// ============================================================================

/**
 * Configuração de email centralizada e segura
 *
 * IMPORTANTE: Estes emails devem corresponder aos configurados no SMTP
 * O servidor SMTP vai REJEITAR qualquer email que não seja autorizado
 *
 * CONFIGURAÇÃO ATUAL: MODO DE TESTE
 * TODO: Alterar para email institucional em produção
 */
const EMAIL_CONFIG = {
  // Email remetente oficial do sistema
  // TODO: Trocar para email institucional IPCA quando em produção
  FROM_EMAIL: process.env.SYSTEM_FROM_EMAIL || "alexandrepv1357@gmail.com",
  FROM_NAME: "Sistema de Ação Social - IPCA",

  // Email para respostas
  REPLY_TO_EMAIL: process.env.SYSTEM_REPLY_TO_EMAIL || "alexandrepv1357@gmail.com",

  // Email de suporte/contacto
  SUPPORT_EMAIL: "sas@ipca.pt",
  SUPPORT_PHONE: "253 802 190",

  // Informações institucionais
  INSTITUTION: {
    name: "Instituto Politécnico do Cávado e do Ave",
    shortName: "IPCA",
    campus: "CAMPUS DO IPCA | BARCELOS",
    address: "Vila Frescaínha S. Martinho",
    postalCode: "4750-810 Barcelos",
    phone: "253 802 190",
  },
} as const;

/**
 * Valida se um email é permitido para envio
 * Previne uso de emails não autorizados
 */
function isAuthorizedSenderEmail(email: string): boolean {
  const authorizedDomains = [
    "@gmail.com",
    "@ipca.pt",
  ];

  // Verifica se o email pertence a um domínio autorizado
  return authorizedDomains.some((domain) => email.toLowerCase().endsWith(domain));
}

/**
 * Retorna o email remetente configurado de forma segura
 * Não permite substituição arbitrária
 */
function getSystemFromEmail(): { email: string; name: string } {
  const email = EMAIL_CONFIG.FROM_EMAIL;

  // Validação adicional de segurança
  if (!isAuthorizedSenderEmail(email)) {
    logger.error(`Unauthorized sender email detected: ${email}`);
    throw new Error("Unauthorized sender email configuration");
  }

  return {
    email: email,
    name: EMAIL_CONFIG.FROM_NAME,
  };
}

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

// ============================================================================
// SCHEDULED FUNCTION - Email de lembrete de distribuição
// ============================================================================

/**
 * Scheduled function que roda diariamente às 18h para enviar lembretes
 * de distribuições agendadas para o dia seguinte
 *
 * Envia email para beneficiários que têm distribuições marcadas para amanhã
 * Agenda: Todos os dias às 18h (Europe/Lisbon timezone)
 */
export const sendDistributionReminders = onSchedule(
  {
    schedule: "0 18 * * *", // Cron: todos os dias às 18h
    timeZone: "Europe/Lisbon",
    region: "europe-west1",
    secrets: ["POSTGRES_PASSWORD"],
  },
  async (event) => {
    logger.info("Starting scheduled check for tomorrow's distributions");

    let pool = null;
    let client = null;

    try {
      pool = await getConnection();
      client = await pool.connect();

      // Calcular data de amanhã
      const tomorrow = new Date();
      tomorrow.setDate(tomorrow.getDate() + 1);
      const tomorrowStr = tomorrow.toISOString().split("T")[0]; // YYYY-MM-DD

      logger.info(`Checking distributions for date: ${tomorrowStr}`);

      // Buscar distribuições agendadas para amanhã com status "POR_ENTREGAR"
      const distributionsQuery = `
        SELECT
          d.id as distribution_id,
          d.distribution_date,
          d.observations,
          b.full_name as beneficiary_name,
          b.contact_number as beneficiary_phone,
          u.email as beneficiary_email,
          u.name as user_name,
          s.description as status_description
        FROM distribution d
        JOIN beneficiary b ON d.beneficiary_id = b.id
        LEFT JOIN "user" u ON b.uid = u.uid
        JOIN status_type s ON d.status_id = s.id
        WHERE d.distribution_date = $1
          AND s.code = 'POR_ENTREGAR'
          AND u.email IS NOT NULL
        ORDER BY b.full_name ASC
      `;

      const distributionsResult = await client.query(distributionsQuery, [tomorrowStr]);
      const distributions = distributionsResult.rows;

      if (distributions.length === 0) {
        logger.info("No distributions scheduled for tomorrow");
        return; // ✅ Retorna void
      }

      logger.info(`Found ${distributions.length} distributions for tomorrow`);

      // Enviar email para cada beneficiário
      const emailPromises = distributions.map((dist: any) => // ✅ Tipo explícito
        sendDistributionReminderEmail(
          dist.beneficiary_email,
          dist.beneficiary_name,
          dist.distribution_date,
          dist.observations
        )
      );

      await Promise.all(emailPromises);

      logger.info(`Successfully sent ${distributions.length} reminder emails`);
    } catch (error: any) {
      logger.error("Error in sendDistributionReminders:", error);
      throw error;
    } finally {
      if (client) {
        client.release();
      }
    }
  }
);

/**
 * Normaliza uma data vinda da BD (pode vir como Date do node-postgres, YYYY-MM-DD ou ISO timestamp)
 * para um Date válido.
 */
function parseDistributionDate(input: string | Date): Date {
  // node-postgres pode devolver Date quando o tipo é DATE/TIMESTAMP
  if (input instanceof Date) {
    if (!Number.isNaN(input.getTime())) {
      return input;
    }
    throw new Error(`Invalid distributionDate Date object: ${String(input)}`);
  }

  const trimmed = (input || "").trim();

  // Caso 1: já é ISO (ex: 2026-01-09T00:00:00Z)
  const isoParsed = new Date(trimmed);
  if (!Number.isNaN(isoParsed.getTime())) {
    return isoParsed;
  }

  // Caso 2: apenas data (YYYY-MM-DD)
  // Força UTC para evitar diferenças de timezone
  const ymdParsed = new Date(`${trimmed}T00:00:00.000Z`);
  if (!Number.isNaN(ymdParsed.getTime())) {
    return ymdParsed;
  }

  throw new Error(`Invalid distributionDate value: ${input}`);
}

/**
 * Constrói o conteúdo HTML do email de lembrete
 */
function buildDistributionReminderEmail(
  beneficiaryName: string,
  distributionDate: string | Date,
  observations: string | null
): string {
  // Formatar data para formato português
  const date = parseDistributionDate(distributionDate);
  const dateFormatted = date.toLocaleDateString("pt-PT", {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
    timeZone: "Europe/Lisbon",
  });

  return `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="UTF-8">
      <style>
        body {
          font-family: Arial, sans-serif;
          line-height: 1.6;
          color: #333;
          margin: 0;
          padding: 0;
        }
        .container {
          max-width: 600px;
          margin: 0 auto;
          padding: 20px;
          background-color: #ffffff;
        }
        .header {
          background-color: #2E7D32;
          color: white;
          padding: 30px 20px;
          text-align: center;
          border-radius: 8px 8px 0 0;
        }
        .header h1 {
          margin: 0;
          font-size: 24px;
        }
        .content {
          background-color: #f9f9f9;
          padding: 30px 20px;
          border-radius: 0 0 8px 8px;
        }
        .info-box {
          background-color: #E8F5E9;
          border-left: 4px solid #2E7D32;
          padding: 15px;
          margin: 20px 0;
        }
        .info-box strong {
          color: #2E7D32;
          font-size: 18px;
        }
        .alert-box {
          background-color: #FFF3CD;
          border-left: 4px solid #FFC107;
          padding: 15px;
          margin: 20px 0;
        }
        .location-box {
          background-color: #E3F2FD;
          border-left: 4px solid #1976D2;
          padding: 15px;
          margin: 20px 0;
        }
        .footer {
          text-align: center;
          margin-top: 30px;
          padding-top: 20px;
          border-top: 1px solid #ddd;
          font-size: 12px;
          color: #666;
        }
        .button {
          display: inline-block;
          background-color: #2E7D32;
          color: white;
          padding: 12px 30px;
          text-decoration: none;
          border-radius: 5px;
          margin: 20px 0;
          font-weight: bold;
        }
        ul {
          padding-left: 20px;
        }
        li {
          margin: 8px 0;
        }
      </style>
    </head>
    <body>
      <div class="container">
        <div class="header">
          <h1>Lembrete de Recolha</h1>
          <p style="margin: 10px 0 0 0; font-size: 16px;">Sistema de Ação Social - IPCA</p>
        </div>
        <div class="content">
          <p>Olá <strong>${beneficiaryName}</strong>,</p>

          <p>Este é um lembrete automático sobre a sua <strong>distribuição agendada</strong>.</p>

          <div class="info-box">
            <strong>📅 Data da Recolha:</strong><br>
            ${dateFormatted}
          </div>

          <div class="alert-box">
            <strong>⚠️ Importante:</strong> A sua distribuição está agendada para <strong>amanhã</strong>.
            Por favor, não se esqueça de comparecer para recolher os produtos.
          </div>

          ${observations ? `
          <div style="background-color: #f5f5f5; padding: 15px; margin: 20px 0; border-radius: 5px;">
            <strong>📝 Observações:</strong><br>
            ${observations}
          </div>
          ` : ""}

          <div class="location-box">
            <strong>📍 Local de Recolha:</strong><br>
            <strong>${EMAIL_CONFIG.INSTITUTION.campus}</strong><br>
            ${EMAIL_CONFIG.INSTITUTION.address}<br>
            ${EMAIL_CONFIG.INSTITUTION.postalCode}<br><br>
            <strong>📞 Telefone:</strong> ${EMAIL_CONFIG.INSTITUTION.phone}
          </div>

          <p><strong>O que levar:</strong></p>
          <ul>
            <li>Documento de identificação (Cartão de Estudante ou BI/CC)</li>
            <li>Sacos ou mochila para transportar os produtos</li>
          </ul>

          <p><strong>Horário de Funcionamento:</strong></p>
          <ul>
            <li>Segunda a Sexta: 9h00 - 17h00</li>
          </ul>

          <p>Caso não possa comparecer, por favor entre em contacto connosco.</p>

          <p style="margin-top: 30px;">
            Se tiver alguma dúvida, não hesite em contactar-nos através do telefone <strong>${EMAIL_CONFIG.SUPPORT_PHONE}</strong>.
          </p>

          <p>Agradecemos a sua colaboração!</p>

          <p style="margin-top: 20px;">
            <strong>Equipa SAS - ${EMAIL_CONFIG.INSTITUTION.shortName}</strong>
          </p>
        </div>
        <div class="footer">
          <p>Este é um email automático. Por favor não responda.</p>
          <p>Sistema de Ação Social - ${EMAIL_CONFIG.INSTITUTION.name}</p>
          <p>© ${new Date().getFullYear()} ${EMAIL_CONFIG.INSTITUTION.shortName} - Todos os direitos reservados</p>
        </div>
      </div>
    </body>
    </html>
  `;
}

/**
 * Envia email de lembrete usando Firebase Admin SDK
 * Usa configuração centralizada e validada
 */
async function sendDistributionReminderEmail(
  toEmail: string,
  beneficiaryName: string,
  distributionDate: string | Date,
  observations: string | null
): Promise<void> {
  try {
    // Validar email destinatário
    if (!toEmail || !toEmail.includes("@")) {
      throw new Error(`Invalid recipient email: ${toEmail}`);
    }

    // Obter configuração segura do remetente
    const sender = getSystemFromEmail();

    const htmlContent = buildDistributionReminderEmail(
      beneficiaryName,
      distributionDate,
      observations
    );

    // Formatar data para o assunto
    const date = parseDistributionDate(distributionDate);
    const dateFormatted = date.toLocaleDateString("pt-PT", {timeZone: "Europe/Lisbon"});

    // Guardar meta apenas como YYYY-MM-DD (sem hora)
    // Usar Europe/Lisbon para alinhar com o timezone do scheduler e UI.
    const distributionDateYmd = new Intl.DateTimeFormat("en-CA", {
      timeZone: "Europe/Lisbon",
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
    }).format(date);

    // Configuração segura do email
    const emailData = {
      to: toEmail,
      from: `${sender.name} <${sender.email}>`,
      replyTo: EMAIL_CONFIG.REPLY_TO_EMAIL,
      message: {
        subject: `Recolha de produtos agendada para amanhã (${dateFormatted})`,
        html: htmlContent,
      },
      createdAt: admin.firestore.FieldValue.serverTimestamp(),
      metadata: {
        type: "distribution_reminder",
        distributionDate: distributionDateYmd,
        sentAt: new Date().toISOString(),
        system: "SAS-IPCA",
      },
    };

    await admin.firestore().collection("mail").add(emailData);

    logger.info(`Distribution reminder email queued for ${toEmail} from ${sender.email}`);
  } catch (error) {
    logger.error(`Error sending distribution reminder to ${toEmail}:`, error);
    throw error;
  }
}

// ============================================================================
// SCHEDULED FUNCTION - Email de alerta de expiração de lotes
// ============================================================================

/**
 * Scheduled function que roda diariamente às 18h para enviar alertas
 * de lotes que vão expirar em 31 dias
 *
 * Envia email para todos os utilizadores com role SAS
 * Agenda: Todos os dias às 18h (Europe/Lisbon timezone)
 */
export const sendLotExpirationAlerts = onSchedule(
  {
    schedule: "0 18 * * *", // Cron: todos os dias às 18h
    timeZone: "Europe/Lisbon",
    region: "europe-west1",
    secrets: ["POSTGRES_PASSWORD"],
  },
  async (event) => {
    logger.info("Starting scheduled check for lots expiring in 31 days");

    let pool = null;
    let client = null;

    try {
      pool = await getConnection();
      client = await pool.connect();

      // Calcular data daqui a 31 dias
      const targetDate = new Date();
      targetDate.setDate(targetDate.getDate() + 31);
      const targetDateStr = targetDate.toISOString().split("T")[0]; // YYYY-MM-DD

      logger.info(`Checking lots expiring on: ${targetDateStr}`);

      // Buscar lotes que expiram daqui a 31 dias (expiration_date não nulo)
      const lotsQuery = `
        SELECT
          l.id,
          l.lot_code,
          l.expiration_date,
          l.current_quantity,
          p.name as product_name,
          p.description as product_description,
          p.unit_of_measure,
          c.name as category_name
        FROM lot l
        JOIN product p ON l.product_id = p.id
        LEFT JOIN category c ON p.category_id = c.id
        WHERE l.expiration_date = $1
          AND l.current_quantity > 0
          AND l.expiration_date IS NOT NULL
        ORDER BY p.name ASC, l.lot_code ASC
      `;

      const lotsResult = await client.query(lotsQuery, [targetDateStr]);
      const expiringLots = lotsResult.rows;

      if (expiringLots.length === 0) {
        logger.info("No lots expiring in 31 days");
        return;
      }

      logger.info(`Found ${expiringLots.length} lots expiring in 31 days`);

      // Buscar todos os utilizadores com role SAS
      const usersQuery = `
        SELECT DISTINCT email, name
        FROM "user"
        WHERE role = 'SAS'
          AND email IS NOT NULL
        ORDER BY name ASC
      `;

      const usersResult = await client.query(usersQuery);
      const sasUsers = usersResult.rows;

      if (sasUsers.length === 0) {
        logger.warn("No active SAS users with email found");
        return;
      }

      logger.info(`Found ${sasUsers.length} SAS users to notify`);

      // Enviar email para cada utilizador SAS
      const emailPromises = sasUsers.map((user: any) =>
        sendLotExpirationAlertEmail(
          user.email,
          user.name,
          expiringLots
        )
      );

      await Promise.all(emailPromises);

      logger.info(`Successfully sent ${sasUsers.length} lot expiration alert emails`);
    } catch (error: any) {
      logger.error("Error in sendLotExpirationAlerts:", error);
      throw error;
    } finally {
      if (client) {
        client.release();
      }
    }
  }
);

/**
 * Constrói o conteúdo HTML do email de alerta de expiração de lotes
 */
function buildLotExpirationAlertEmail(
  userName: string,
  expiringLots: any[]
): string {
  // Construir lista de lotes
  const lotsListHtml = expiringLots.map((lot) => {
    const expirationDate = parseDistributionDate(lot.expiration_date);
    const dateFormatted = expirationDate.toLocaleDateString("pt-PT", {
      year: "numeric",
      month: "long",
      day: "numeric",
      timeZone: "Europe/Lisbon",
    });

    return `
      <tr style="border-bottom: 1px solid #ddd;">
        <td style="padding: 12px 8px;">${lot.product_name}</td>
        <td style="padding: 12px 8px; text-align: center;"><strong>${lot.lot_code}</strong></td>
        <td style="padding: 12px 8px; text-align: center;">${lot.current_quantity}</td>
        <td style="padding: 12px 8px; text-align: center; color: #D32F2F; font-weight: bold;">${dateFormatted}</td>
      </tr>
    `;
  }).join("");

  return `
    <!DOCTYPE html>
    <html>
    <head>
      <meta charset="UTF-8">
      <style>
        body {
          font-family: Arial, sans-serif;
          line-height: 1.6;
          color: #333;
          margin: 0;
          padding: 0;
        }
        .container {
          max-width: 700px;
          margin: 0 auto;
          padding: 20px;
          background-color: #ffffff;
        }
        .header {
          background-color: #D32F2F;
          color: white;
          padding: 30px 20px;
          text-align: center;
          border-radius: 8px 8px 0 0;
        }
        .header h1 {
          margin: 0;
          font-size: 24px;
        }
        .content {
          background-color: #f9f9f9;
          padding: 30px 20px;
          border-radius: 0 0 8px 8px;
        }
        .alert-box {
          background-color: #FFEBEE;
          border-left: 4px solid #D32F2F;
          padding: 15px;
          margin: 20px 0;
        }
        .alert-box strong {
          color: #D32F2F;
        }
        .info-box {
          background-color: #E3F2FD;
          border-left: 4px solid #1976D2;
          padding: 15px;
          margin: 20px 0;
        }
        table {
          width: 100%;
          border-collapse: collapse;
          margin: 20px 0;
          background-color: white;
        }
        th {
          background-color: #D32F2F;
          color: white;
          padding: 12px 8px;
          text-align: left;
          font-weight: bold;
        }
        td {
          padding: 12px 8px;
        }
        .footer {
          text-align: center;
          margin-top: 30px;
          padding-top: 20px;
          border-top: 1px solid #ddd;
          font-size: 12px;
          color: #666;
        }
        .stats-box {
          background-color: #FFF3CD;
          padding: 15px;
          margin: 20px 0;
          border-radius: 5px;
          text-align: center;
        }
        .stats-box .number {
          font-size: 32px;
          font-weight: bold;
          color: #D32F2F;
        }
      </style>
    </head>
    <body>
      <div class="container">
        <div class="header">
          <h1>Alerta de Expiração de Lotes</h1>
          <p style="margin: 10px 0 0 0; font-size: 16px;">Sistema de Ação Social - IPCA</p>
        </div>
        <div class="content">
          <p>Olá <strong>${userName}</strong>,</p>

          <p>Este é um alerta automático do sistema de gestão de stock.</p>

          <div class="alert-box">
            <strong>⚠️ Atenção:</strong> Existem lotes que vão <strong>expirar em 31 dias</strong>.
            É necessário tomar ações para evitar desperdício de produtos.
          </div>

          <div class="stats-box">
            <div class="number">${expiringLots.length}</div>
            <div>Lote(s) a expirar em 31 dias</div>
          </div>

          <h3 style="color: #D32F2F; margin-top: 30px;">📦 Lotes Identificados:</h3>

          <table>
            <thead>
              <tr>
                <th>Produto</th>
                <th style="text-align: center;">Código do Lote</th>
                <th style="text-align: center;">Quantidade</th>
                <th style="text-align: center;">Data de Expiração</th>
              </tr>
            </thead>
            <tbody>
              ${lotsListHtml}
            </tbody>
          </table>

          <div class="info-box">
            <strong>📋 Ações Recomendadas:</strong>
            <ul style="margin: 10px 0 0 0; padding-left: 20px;">
              <li>Priorizar estes lotes nas próximas distribuições</li>
              <li>Verificar se há beneficiários que necessitem destes produtos</li>
              <li>Considerar redistribuição para outras instituições se necessário</li>
              <li>Atualizar o sistema após tomar as devidas ações</li>
            </ul>
          </div>

          <p style="margin-top: 30px;">
            Para mais informações ou esclarecimentos, contacte o administrador do sistema.
          </p>

          <p style="margin-top: 20px;">
            <strong>Equipa SAS - ${EMAIL_CONFIG.INSTITUTION.shortName}</strong>
          </p>
        </div>
        <div class="footer">
          <p>Este é um email automático. Por favor não responda.</p>
          <p>Sistema de Ação Social - ${EMAIL_CONFIG.INSTITUTION.name}</p>
          <p>© ${new Date().getFullYear()} ${EMAIL_CONFIG.INSTITUTION.shortName} - Todos os direitos reservados</p>
        </div>
      </div>
    </body>
    </html>
  `;
}

/**
 * Envia email de alerta de expiração de lotes
 */
async function sendLotExpirationAlertEmail(
  toEmail: string,
  userName: string,
  expiringLots: any[]
): Promise<void> {
  try {
    // Validar email destinatário
    if (!toEmail || !toEmail.includes("@")) {
      throw new Error(`Invalid recipient email: ${toEmail}`);
    }

    // Obter configuração segura do remetente
    const sender = getSystemFromEmail();

    const htmlContent = buildLotExpirationAlertEmail(userName, expiringLots);

    const lotsCount = expiringLots.length;
    const lotsCodes = expiringLots.map((l) => l.lot_code).join(", ");

    // Configuração segura do email
    const emailData = {
      to: toEmail,
      from: `${sender.name} <${sender.email}>`,
      replyTo: EMAIL_CONFIG.REPLY_TO_EMAIL,
      message: {
        subject: `Alerta: ${lotsCount} lote(s) a expirar em 31 dias`,
        html: htmlContent,
      },
      createdAt: admin.firestore.FieldValue.serverTimestamp(),
      metadata: {
        type: "lot_expiration_alert",
        lotsCount: lotsCount,
        lotsCodes: lotsCodes,
        sentAt: new Date().toISOString(),
        system: "SAS-IPCA",
      },
    };

    await admin.firestore().collection("mail").add(emailData);

    logger.info(`Lot expiration alert email queued for ${toEmail} from ${sender.email}`);
  } catch (error) {
    logger.error(`Error sending lot expiration alert to ${toEmail}:`, error);
    throw error;
  }
}

