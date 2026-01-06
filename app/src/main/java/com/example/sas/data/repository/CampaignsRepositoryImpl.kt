package com.example.sas.data.repository

import com.example.sas.data.datasource.CampaignsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.repositories.CampaignsRepository
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CampaignsRepositoryImpl @Inject constructor(
    private val dataSource: CampaignsDataSource
) : CampaignsRepository {

    override fun createCampaign(name: String, startDate: LocalDate, endDate: LocalDate, address: String, type: String, observations: String?, userId: String?, statusId: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createCampaign(name, startDate, endDate, address, type, observations, userId, statusId)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar campanha"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateCampaign(id: String, name: String?, startDate: LocalDate?, endDate: LocalDate?, address: String?, type: String?, observations: String?, userId: String?, statusId: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateCampaign(id, name, startDate, endDate, address, type, observations, userId, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar campanha"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateCampaignStatus(id: String, statusId: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateCampaignStatus(id, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteCampaign(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteCampaign(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao eliminar campanha"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCampaignById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getCampaignById(id)
            val campaign = item?.let {
                Campaign(
                    id = it.id.toString(),
                    name = it.campaignName,
                    startDate = formatDataConnectDate(it.campaignStartDate),
                    endDate = formatDataConnectDate(it.campaignEndDate),
                    address = it.address,
                    type = it.type,
                    observations = it.observations,
                    responsibleName = it.user?.name, // Mapeado do User object
                    status = it.status?.let { s -> CampaignStatus(s.id.toString(), s.code, s.description, s.color) },
                    createdAt = formatTimestamp(it.createdAt),
                    updatedAt = formatTimestamp(it.updatedAt)
                )
            }
            emit(ResultWrapper.Success(campaign))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar campanha"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listCampaigns(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listCampaigns(limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar campanhas"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchCampaigns(query: String?, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchCampaigns(query, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao pesquisar campanhas"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listCampaignsByDateRange(startDate: LocalDate, endDate: LocalDate, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listCampaignsByDateRange(startDate, endDate, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar campanhas por data"))
        }
    }.flowOn(Dispatchers.IO)

    // Mappers para os itens de lista (convertendo campos do DataConnect para Campaign)
    private fun com.google.firebase.dataconnect.generated.ListCampaignsQuery.Data.CampaignsItem.toDomain() = Campaign(
        id = id.toString(),
        name = campaignName,
        startDate = formatDataConnectDate(campaignStartDate),
        endDate = formatDataConnectDate(campaignEndDate),
        address = address,
        type = type,
        observations = observations,
        responsibleName = user?.name,
        status = status?.let { CampaignStatus(it.id.toString(), it.code, it.description, null) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun com.google.firebase.dataconnect.generated.SearchCampaignsQuery.Data.CampaignsItem.toDomain() = Campaign(
        id = id.toString(),
        name = campaignName,
        startDate = formatDataConnectDate(campaignStartDate),
        endDate = formatDataConnectDate(campaignEndDate),
        address = address,
        type = type,
        observations = observations,
        responsibleName = null, // Search não traz o objeto User no GQL fornecido
        status = status?.let { CampaignStatus(it.id.toString(), it.code, it.description, null) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun com.google.firebase.dataconnect.generated.ListCampaignsByDateRangeQuery.Data.CampaignsItem.toDomain() = Campaign(
        id = id.toString(),
        name = campaignName,
        startDate = formatDataConnectDate(campaignStartDate),
        endDate = formatDataConnectDate(campaignEndDate),
        address = address,
        type = type,
        observations = observations,
        responsibleName = null,
        status = status?.let { CampaignStatus(it.id.toString(), it.code, it.description, null) },
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun formatDataConnectDate(date: LocalDate): String {
        return "${date.day.toString().padStart(2, '0')}/${date.month.toString().padStart(2, '0')}/${date.year}"
    }

    private fun formatTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}