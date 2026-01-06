// com.example.sas.data.repository.DonationsRepositoryImpl.kt
package com.example.sas.data.repository

import com.example.sas.data.datasource.DonationsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.repositories.DonationsRepository
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class DonationsRepositoryImpl @Inject constructor(
    private val dataSource: DonationsDataSource
) : DonationsRepository {

    override fun createDonation(date: LocalDate, name: String, value: Double, contact: String?, obs: String?, campaignId: String?, statusId: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createDonation(date, name, value, contact, obs, campaignId, statusId)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun updateDonation(id: String, date: LocalDate?, name: String?, value: Double?, contact: String?, obs: String?, campaignId: String?, statusId: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateDonation(id, date, name, value, contact, obs, campaignId, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun updateDonationStatus(id: String, statusId: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateDonationStatus(id, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun deleteDonation(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteDonation(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun getDonationById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val it = dataSource.getDonationById(id)
            val donation = it?.let {
                Donation(
                    id = it.id.toString(),
                    donationDate = formatDataConnectDate(it.donationDate),
                    donorName = it.donorName,
                    estimatedValue = it.estimatedValue,
                    donorContact = it.donorContact,
                    observations = it.observations,
                    campaignName = it.campaign?.campaignName,
                    status = it.status?.let { s -> DonationStatus(s.id.toString(), s.code, s.description, s.color) },
                    createdAt = formatTimestamp(it.createdAt),
                    updatedAt = formatTimestamp(it.updatedAt)
                )
            }
            emit(ResultWrapper.Success(donation))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun listDonations(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDonations(limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun searchDonations(query: String?, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchDonations(query, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun createDonationItem(donationId: String, productId: String, quantity: Int, stateId: String?, obs: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.createDonationItem(donationId, productId, quantity, stateId, obs)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun updateDonationItem(donationId: String, productId: String, quantity: Int?, stateId: String?, obs: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateDonationItem(donationId, productId, quantity, stateId, obs)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    override fun deleteDonationItem(donationId: String, productId: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteDonationItem(donationId, productId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    // No ficheiro com.example.sas.data.repository.DonationsRepositoryImpl.kt

    override fun listDonationsByCampaign(campaignId: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDonationsByCampaign(campaignId, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar doações da campanha"))
        }
    }.flowOn(Dispatchers.IO)

    private fun com.google.firebase.dataconnect.generated.ListDonationsByCampaignQuery.Data.DonationsItem.toDomain() = Donation(
        id = id.toString(),
        donationDate = formatDataConnectDate(donationDate),
        donorName = donorName,
        estimatedValue = estimatedValue,
        donorContact = donorContact,
        observations = observations,
        campaignName = null, // Nesta query o objeto campaign não vem no retorno (conforme o .generated fornecido)
        status = status?.let { DonationStatus(it.id.toString(), it.code, it.description) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    override fun listDonationItemsByDonation(donationId: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDonationItemsByDonation(donationId, limit, offset)
            emit(ResultWrapper.Success(items.map {
                DonationItem(
                    donationId = it.donation.id.toString(),
                    productId = it.product.id.toString(),
                    quantity = it.quantity,
                    categoryName = it.product.category?.name,
                    state = it.state?.let { s -> StatusType(s.id.toString(), s.code, s.description) },
                    observations = it.observations,
                    createdAt = formatTimestamp(it.createdAt)
                )
            }))
        } catch (e: Exception) { emit(ResultWrapper.Error(e.message ?: "Erro")) }
    }.flowOn(Dispatchers.IO)

    // Helpers
    private fun com.google.firebase.dataconnect.generated.ListDonationsQuery.Data.DonationsItem.toDomain() = Donation(
        id = id.toString(),
        donationDate = formatDataConnectDate(donationDate),
        donorName = donorName,
        estimatedValue = estimatedValue,
        donorContact = donorContact,
        observations = observations,
        campaignName = campaign?.campaignName,
        status = status?.let { DonationStatus(it.id.toString(), it.code, it.description) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun com.google.firebase.dataconnect.generated.SearchDonationsQuery.Data.DonationsItem.toDomain() = Donation(
        id = id.toString(),
        donationDate = formatDataConnectDate(donationDate),
        donorName = donorName,
        estimatedValue = estimatedValue,
        donorContact = donorContact,
        observations = observations,
        campaignName = null,
        status = status?.let { DonationStatus(it.id.toString(), it.code, it.description) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun formatDataConnectDate(date: LocalDate) = "${date.day.toString().padStart(2, '0')}/${date.month.toString().padStart(2, '0')}/${date.year}"
    private fun formatTimestamp(ts: Timestamp) = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(ts.toDate())
}