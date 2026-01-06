// com.example.sas.domain.repositories.DonationsRepository.kt
package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Donation
import com.example.sas.domain.models.DonationItem
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.flow.Flow

interface DonationsRepository {
    fun createDonation(date: LocalDate, name: String, value: Double, contact: String?, obs: String?, campaignId: String?, statusId: String?): Flow<ResultWrapper<String>>
    fun updateDonation(id: String, date: LocalDate?, name: String?, value: Double?, contact: String?, obs: String?, campaignId: String?, statusId: String?): Flow<ResultWrapper<Unit>>
    fun updateDonationStatus(id: String, statusId: String): Flow<ResultWrapper<Unit>>
    fun deleteDonation(id: String): Flow<ResultWrapper<Unit>>
    fun getDonationById(id: String): Flow<ResultWrapper<Donation?>>
    fun listDonations(limit: Int?, offset: Int?): Flow<ResultWrapper<List<Donation>>>
    fun searchDonations(query: String?, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Donation>>>

    fun createDonationItem(donationId: String, productId: String, quantity: Int, stateId: String?, obs: String?): Flow<ResultWrapper<Unit>>
    fun updateDonationItem(donationId: String, productId: String, quantity: Int?, stateId: String?, obs: String?): Flow<ResultWrapper<Unit>>
    fun deleteDonationItem(donationId: String, productId: String): Flow<ResultWrapper<Unit>>
    fun listDonationItemsByDonation(donationId: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<DonationItem>>>
    fun listDonationsByCampaign(campaignId: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Donation>>>
}