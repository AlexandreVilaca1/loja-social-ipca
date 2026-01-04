package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.DistributionKey
import com.google.firebase.dataconnect.generated.ListDistributionItemsByDistributionQuery
import com.google.firebase.dataconnect.generated.LotKey
import com.google.firebase.dataconnect.generated.SasConnectorConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for distribution items using Firebase Data Connect.
 */
@Singleton
class DistributionItemsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists distribution items by distribution ID.
     */
    suspend fun listItemsByDistribution(
        distributionId: String,
        limit: Int,
        offset: Int
    ): List<ListDistributionItemsByDistributionQuery.Data.DistributionItemsItem> {
        val result = connector.listDistributionItemsByDistribution.execute(
            distributionId = java.util.UUID.fromString(distributionId)
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.distributionItems
    }

    /**
     * Creates a new distribution item.
     */
    suspend fun createDistributionItem(
        distributionId: String,
        lotId: String,
        quantity: Int,
        observations: String?
    ) {
        //Parametros obrigatórios vao dentro do execute
        connector.createDistributionItem.execute(
            distributionId = DistributionKey(java.util.UUID.fromString(distributionId)),
            lotId = LotKey(java.util.UUID.fromString(lotId)),
            quantity = quantity
        ) {
            //Parametros opcionais vao dentro do bloco
            this.observations = observations
        }
    }
}

