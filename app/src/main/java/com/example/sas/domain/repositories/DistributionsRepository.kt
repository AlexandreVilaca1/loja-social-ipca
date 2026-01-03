package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for distributions operations.
 */
interface DistributionsRepository {

    /**
     * Lists all distributions.
     */
    fun listDistributions(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>>

    /**
     * Lists distributions by beneficiary.
     * @param beneficiaryId UUID of the beneficiary
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distributions
     */
    fun listDistributionsByBeneficiary(
        beneficiaryId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>>

    fun listDistributionsByStatus(
        statusCode: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>>
}
