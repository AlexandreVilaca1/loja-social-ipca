package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateLotRequest
import com.example.sas.domain.models.Lot
import com.example.sas.domain.models.UpdateLotRequest
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for lots (lotes/batches).
 */
interface LotsRepository {

    /**
     * Lists all lots.
     */
    fun listLots(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>>

    /**
     * Lists lots by product.
     */
    fun listLotsByProduct(
        productId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>>

    /**
     * Searches lots by lot code.
     */
    fun searchLots(
        lotCode: String?,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>>

    /**
     * Gets a specific lot by ID.
     */
    fun getLotById(id: String): Flow<ResultWrapper<Lot>>

    /**
     * Creates a new lot.
     */
    fun createLot(request: CreateLotRequest): Flow<ResultWrapper<String>>

    /**
     * Updates an existing lot.
     */
    fun updateLot(request: UpdateLotRequest): Flow<ResultWrapper<Unit>>

    /**
     * Updates only the quantity of a lot.
     */
    fun updateLotQuantity(
        id: String,
        currentQuantity: Int
    ): Flow<ResultWrapper<Unit>>

    /**
     * Deletes a lot.
     */
    fun deleteLot(id: String): Flow<ResultWrapper<Unit>>
}