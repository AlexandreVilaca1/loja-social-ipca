package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.DistributionItem
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for distribution items operations.
 */
interface DistributionItemsRepository {

    /**
     * Lists distribution items by distribution ID.
     * @param distributionId UUID of the distribution
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distribution items
     */
    fun listItemsByDistribution(
        distributionId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<DistributionItem>>>

    /**
     * Creates a new distribution item.
     * @param distributionId UUID of the distribution
     * @param lotId UUID of the lot
     * @param quantity Quantity to distribute
     * @param observations Optional observations
     * @return Flow with result wrapper indicating success or failure
     */
    fun createDistributionItem(
        distributionId: String,
        lotId: String,
        quantity: Int,
        observations: String?
    ): Flow<ResultWrapper<Unit>>
}

