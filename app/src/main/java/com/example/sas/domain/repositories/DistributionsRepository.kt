package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for distributions operations.
 */
interface DistributionsRepository {

    /**
     * Gets a distribution by its ID.
     * @param distributionId UUID of the distribution
     * @return Flow with result wrapper containing the distribution
     */
    fun getDistributionById(distributionId: String): Flow<ResultWrapper<Distribution?>>

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

    /**
     * Lists distributions by beneficiary and status.
     * @param beneficiaryId UUID of the beneficiary
     * @param statusCode Status code to filter by
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distributions
     */
    fun listDistributionsByBeneficiaryAndStatus(
        beneficiaryId: String,
        statusCode: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>>

    fun listDistributionsByStatus(
        statusCode: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>>

    /**
     * Creates a new distribution.
     * @param beneficiaryId UUID of the beneficiary
     * @param distributionDate Date of the distribution
     * @param responsibleStaffId UUID of the responsible staff member
     * @param statusId UUID of the status
     * @param observations Optional observations
     * @return Flow with result wrapper containing the created distribution ID
     */
    fun createDistribution(
        beneficiaryId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): Flow<ResultWrapper<String>>

    /**
     * Updates the status of a distribution.
     * @param distributionId UUID of the distribution
     * @param statusId UUID of the new status
     * @return Flow with result wrapper indicating success or failure
     */
    fun updateDistributionStatus(
        distributionId: String,
        statusId: String
    ): Flow<ResultWrapper<Unit>>

    /**
     * Updates a distribution.
     * @param distributionId UUID of the distribution
     * @param distributionDate Date of the distribution
     * @param responsibleStaffId UUID of the responsible staff member
     * @param statusId UUID of the status
     * @param observations Optional observations
     * @return Flow with result wrapper indicating success or failure
     */
    fun updateDistribution(
        distributionId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): Flow<ResultWrapper<Unit>>
}
