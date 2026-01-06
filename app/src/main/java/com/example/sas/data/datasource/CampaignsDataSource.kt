package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import com.google.firebase.dataconnect.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CampaignsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector get() = SasConnectorConnector.instance

    suspend fun createCampaign(
        name: String,
        startDate: LocalDate,
        endDate: LocalDate,
        address: String,
        type: String,
        observations: String?,
        userId: String?,
        statusId: String?
    ): String {
        val result = connector.createCampaign.execute(name, startDate, endDate, address, type) {
            this.observations = observations
            this.userId = userId?.let { UserKey(UUID.fromString(it)) }
            this.statusId = statusId?.let { StatusTypeKey(UUID.fromString(it)) }
        }
        return result.data.campaign_insert.id.toString()
    }

    suspend fun updateCampaign(
        id: String,
        name: String?,
        startDate: LocalDate?,
        endDate: LocalDate?,
        address: String?,
        type: String?,
        observations: String?,
        userId: String?,
        statusId: String?
    ) = connector.updateCampaign.execute(UUID.fromString(id)) {
        this.campaignName = name
        this.campaignStartDate = startDate
        this.campaignEndDate = endDate
        this.address = address
        this.type = type
        this.observations = observations
        this.userId = userId?.let { UserKey(UUID.fromString(it)) }
        this.statusId = statusId?.let { StatusTypeKey(UUID.fromString(it)) }
    }

    suspend fun updateCampaignStatus(id: String, statusId: String) =
        connector.updateCampaignStatus.execute(UUID.fromString(id), StatusTypeKey(UUID.fromString(statusId)))

    suspend fun deleteCampaign(id: String) =
        connector.deleteCampaign.execute(UUID.fromString(id))

    suspend fun getCampaignById(id: String) =
        connector.getCampaignById.execute(UUID.fromString(id)).data.campaign

    suspend fun listCampaigns(limit: Int?, offset: Int?) =
        connector.listCampaigns.execute {
            limit?.let { this.limit = it }
            offset?.let { this.offset = it }
        }.data.campaigns

    suspend fun searchCampaigns(query: String?, limit: Int?, offset: Int?) =
        connector.searchCampaigns.execute {
            this.campaignName = query
            limit?.let { this.limit = it }
            offset?.let { this.offset = it }
        }.data.campaigns

    suspend fun listCampaignsByDateRange(startDate: LocalDate, endDate: LocalDate, limit: Int?, offset: Int?) =
        connector.listCampaignsByDateRange.execute(startDate, endDate) {
            limit?.let { this.limit = it }
            offset?.let { this.offset = it }
        }.data.campaigns
}