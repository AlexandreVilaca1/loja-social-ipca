// com.example.sas.data.datasource.DonationsDataSource.kt
package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import com.google.firebase.dataconnect.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DonationsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector get() = SasConnectorConnector.instance

    // --- Donations ---

    suspend fun createDonation(
        date: LocalDate, name: String, value: Double, contact: String?, obs: String?, campaignId: String?
    ): String {
        val result = connector.createDonation.execute(date, name, value) {
            this.donorContact = contact
            this.observations = obs
            this.campaignId = campaignId?.let { CampaignKey(UUID.fromString(it)) }
        }
        return result.data.donation_insert.id.toString()
    }

    suspend fun updateDonation(
        id: String, date: LocalDate?, name: String?, value: Double?, contact: String?, obs: String?, campaignId: String?
    ) = connector.updateDonation.execute(UUID.fromString(id)) {
        this.donationDate = date
        this.donorName = name
        this.estimatedValue = value
        this.donorContact = contact
        this.observations = obs
        this.campaignId = campaignId?.let { CampaignKey(UUID.fromString(it)) }
    }

    suspend fun deleteDonation(id: String) = connector.deleteDonation.execute(UUID.fromString(id))

    suspend fun getDonationById(id: String) = connector.getDonationById.execute(UUID.fromString(id)).data.donation

    suspend fun listDonations(limit: Int?, offset: Int?) = connector.listDonations.execute {
        this.limit = limit; this.offset = offset
    }.data.donations

    suspend fun searchDonations(query: String?, limit: Int?, offset: Int?) = connector.searchDonations.execute {
        this.donorName = query; this.limit = limit; this.offset = offset
    }.data.donations

    suspend fun listDonationsByCampaign(campaignId: String, limit: Int?, offset: Int?) =
        connector.listDonationsByCampaign.execute(UUID.fromString(campaignId)) {
            this.limit = limit; this.offset = offset
        }.data.donations

    // --- Donation Items ---

    suspend fun createDonationItem(donationId: String, productId: String, quantity: Int, obs: String?) =
        connector.createDonationItem.execute(DonationKey(UUID.fromString(donationId)), ProductKey(UUID.fromString(productId)), quantity) {
            this.observations = obs
        }

    suspend fun updateDonationItem(donationId: String, productId: String, quantity: Int?, obs: String?) =
        connector.updateDonationItem.execute(UUID.fromString(donationId), UUID.fromString(productId)) {
            this.quantity = quantity
            this.observations = obs
        }

    suspend fun deleteDonationItem(donationId: String, productId: String) =
        connector.deleteDonationItem.execute(UUID.fromString(donationId), UUID.fromString(productId))

    suspend fun listDonationItemsByDonation(donationId: String, limit: Int?, offset: Int?) =
        connector.listDonationItemsByDonation.execute(UUID.fromString(donationId)) {
            this.limit = limit; this.offset = offset
        }.data.donationItems

    suspend fun listDonationItemsByProduct(productId: String, limit: Int?, offset: Int?) =
        connector.listDonationItemsByProduct.execute(UUID.fromString(productId)) {
            this.limit = limit; this.offset = offset
        }.data.donationItems
}