package com.example.sas.data.datasource

import com.google.firebase.dataconnect.LocalDate
import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for lots (lotes) using Firebase Data Connect.
 * Handles direct communication with Firebase backend for inventory lot management.
 */
@Singleton
class LotsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all lots from Firebase Data Connect.
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of all lots with product information
     */
    suspend fun listLots(
        limit: Int,
        offset: Int
    ): List<ListLotsQuery.Data.LotsItem> {
        val result = connector.listLots.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.lots
    }

    /**
     * Lists lots by product ID.
     * @param productId UUID of the product
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of lots for the specified product
     */
    suspend fun listLotsByProduct(
        productId: String,
        limit: Int,
        offset: Int
    ): List<ListLotsByProductQuery.Data.LotsItem> {
        val result = connector.listLotsByProduct.execute(
            productId = UUID.fromString(productId)
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.lots
    }

    /**
     * Searches lots by lot code.
     * @param lotCode Lot code to search for
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of lots matching the search criteria
     */
    suspend fun searchLots(
        lotCode: String?,
        limit: Int,
        offset: Int
    ): List<SearchLotsQuery.Data.LotsItem> {
        val result = connector.searchLots.execute {
            this.lotCode = lotCode
            this.limit = limit
            this.offset = offset
        }
        return result.data.lots
    }

    /**
     * Gets a specific lot by ID.
     * @param id UUID of the lot
     * @return Lot details with product information, or null if not found
     */
    suspend fun getLotById(id: String): GetLotByIdQuery.Data.Lot? {
        val result = connector.getLotById.execute(
            id = UUID.fromString(id)
        )
        return result.data.lot
    }

    /**
     * Creates a new lot in Firebase Data Connect.
     * @param initialQuantity Initial quantity of the lot
     * @param currentQuantity Current quantity of the lot
     * @param entryDate Date the lot entered the inventory
     * @param lotCode Unique code for the lot
     * @param expirationDate Expiration date of the lot
     * @param observations Optional observations about the lot
     * @param productId Optional UUID of the associated product
     * @return The UUID of the created lot
     */
    suspend fun createLot(
        initialQuantity: Int,
        currentQuantity: Int,
        entryDate: LocalDate,
        lotCode: String,
        expirationDate: LocalDate,
        observations: String?,
        productId: String?
    ): String {
        val result = connector.createLot.execute(
            initialQuantity = initialQuantity,
            currentQuantity = currentQuantity,
            entryDate = entryDate,
            lotCode = lotCode,
            expirationDate = expirationDate
        ) {
            this.observations = observations
            this.productId = productId?.let { ProductKey(UUID.fromString(it)) }
        }
        return result.data.lot_insert.id.toString()
    }

    /**
     * Updates an existing lot in Firebase Data Connect.
     * All parameters except id are optional.
     * @param id UUID of the lot to update
     * @param initialQuantity New initial quantity (optional)
     * @param currentQuantity New current quantity (optional)
     * @param entryDate New entry date (optional)
     * @param lotCode New lot code (optional)
     * @param expirationDate New expiration date (optional)
     * @param observations New observations (optional)
     * @param productId New product ID (optional)
     */
    suspend fun updateLot(
        id: String,
        initialQuantity: Int? = null,
        currentQuantity: Int? = null,
        entryDate: LocalDate? = null,
        lotCode: String? = null,
        expirationDate: LocalDate? = null,
        observations: String? = null,
        productId: String? = null
    ) {
        connector.updateLot.execute(
            id = UUID.fromString(id)
        ) {
            this.initialQuantity = initialQuantity
            this.currentQuantity = currentQuantity
            this.entryDate = entryDate
            this.lotCode = lotCode
            this.expirationDate = expirationDate
            this.observations = observations
            this.productId = productId?.let { ProductKey(UUID.fromString(it)) }
        }
    }

    /**
     * Updates only the current quantity of a lot.
     * Useful for inventory adjustments without affecting other fields.
     * @param id UUID of the lot
     * @param currentQuantity New current quantity
     */
    suspend fun updateLotQuantity(
        id: String,
        currentQuantity: Int
    ) {
        connector.updateLotQuantity.execute(
            id = UUID.fromString(id),
            currentQuantity = currentQuantity
        )
    }

    /**
     * Deletes a lot from Firebase Data Connect.
     * @param id UUID of the lot to delete
     */
    suspend fun deleteLot(id: String) {
        connector.deleteLot.execute(
            id = UUID.fromString(id)
        )
    }
}