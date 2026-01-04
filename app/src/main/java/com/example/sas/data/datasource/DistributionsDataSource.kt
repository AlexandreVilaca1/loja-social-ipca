package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.BeneficiaryKey
import com.google.firebase.dataconnect.generated.ListDistributionsByBeneficiaryAndStatusQuery
import com.google.firebase.dataconnect.generated.ListDistributionsByBeneficiaryQuery
import com.google.firebase.dataconnect.generated.ListDistributionsByStatusQuery
import com.google.firebase.dataconnect.generated.ListDistributionsQuery
import com.google.firebase.dataconnect.generated.SasConnectorConnector
import com.google.firebase.dataconnect.generated.StatusTypeKey
import com.google.firebase.dataconnect.generated.UserKey
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for distributions using Firebase Data Connect.
 */
@Singleton
class DistributionsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all distributions from Firebase Data Connect.
     */
    suspend fun listDistributions(
        limit: Int,
        offset: Int
    ): List<ListDistributionsQuery.Data.DistributionsItem> {
        val result = connector.listDistributions.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.distributions
    }

    /**
     * Lists distributions by beneficiary from Firebase Data Connect.
     */
    suspend fun listDistributionsByBeneficiary(
        beneficiaryId: String,
        limit: Int,
        offset: Int
    ): List<ListDistributionsByBeneficiaryQuery.Data.DistributionsItem> {
        val result = connector.listDistributionsByBeneficiary.execute(
            beneficiaryId = java.util.UUID.fromString(beneficiaryId)
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.distributions
    }

    /**
     * Lists distributions by beneficiary and status from Firebase Data Connect.
     */
    suspend fun listDistributionsByBeneficiaryAndStatus(
        beneficiaryId: String,
        statusCode: String,
        limit: Int,
        offset: Int
    ): List<ListDistributionsByBeneficiaryAndStatusQuery.Data.DistributionsItem> {
        val result = connector.listDistributionsByBeneficiaryAndStatus.execute(
            beneficiaryId = java.util.UUID.fromString(beneficiaryId),
            statusCode = statusCode
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.distributions
    }

    /**
     * Lists distributions by status code from Firebase Data Connect.
     * @param statusCode The status code to filter by (e.g., "PENDING", "COMPLETED", "CANCELLED")
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of distributions matching the status code
     */
    suspend fun listDistributionsByStatus(
        statusCode: String,
        limit: Int,
        offset: Int
    ): List<ListDistributionsByStatusQuery.Data.DistributionsItem> {
        val result = connector.listDistributionsByStatus.execute(
            statusCode = statusCode
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.distributions
    }

    /**
     * Creates a new distribution in Firebase Data Connect.
     * @param beneficiaryId UUID of the beneficiary
     * @param distributionDate Date of the distribution (format: YYYY-MM-DD)
     * @param responsibleStaffId UUID of the responsible staff member
     * @param statusId UUID of the status
     * @param observations Optional observations
     * @return The ID of the created distribution
     */
    suspend fun createDistribution(
        beneficiaryId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): String {
        // Parse date string (format: YYYY-MM-DD)
        val dateParts = distributionDate.split("-")
        val localDate = com.google.firebase.dataconnect.LocalDate(
            year = dateParts[0].toInt(),
            month = dateParts[1].toInt(),
            day = dateParts[2].toInt()
        )

        val result = connector.createDistribution.execute(
            distributionDate = localDate
        ) {
            this.observations = observations
            this.responsibleStaffId = UserKey(java.util.UUID.fromString(responsibleStaffId))
            this.beneficiaryId = BeneficiaryKey(java.util.UUID.fromString(beneficiaryId))
            this.statusId = StatusTypeKey(java.util.UUID.fromString(statusId))
        }
        return result.data.distribution_insert.id.toString()
    }
}

