package com.example.sas.data.datasource
import com.google.firebase.dataconnect.generated.ListDistributionsByBeneficiaryQuery
import com.google.firebase.dataconnect.generated.SasConnectorConnector
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
}
