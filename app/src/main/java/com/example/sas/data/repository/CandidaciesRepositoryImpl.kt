package com.example.sas.data.repository

import com.example.sas.data.datasource.CandidaciesDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Candidacy
import com.example.sas.domain.models.CandidacyStatus
import com.example.sas.domain.repositories.CandidaciesRepository
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CandidaciesRepositoryImpl @Inject constructor(
    private val dataSource: CandidaciesDataSource
) : CandidaciesRepository {

    override fun createCandidacy(applicationDate: LocalDate, statusId: String?, observations: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createCandidacy(applicationDate, statusId, observations)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun approveCandidacy(id: String, statusId: String, observations: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.approveCandidacy(id, statusId, observations)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao aprovar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun rejectCandidacy(id: String, statusId: String, observations: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.rejectCandidacy(id, statusId, observations)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao rejeitar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCandidacyById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getCandidacyById(id)
            val candidacy = item?.let {
                Candidacy(
                    it.id.toString(),
                    it.applicationDate.toString(),
                    it.status?.let { s -> CandidacyStatus(s.id.toString(), s.code, s.description, s.color, s.sortOrder) },
                    it.observations,
                    formatTimestamp(it.createdAt),
                    formatTimestamp(it.updatedAt)
                )
            }
            emit(ResultWrapper.Success(candidacy))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listCandidacies(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listCandidacies(limit, offset)
            val list = items.map { it.toDomain() }
            emit(ResultWrapper.Success(list))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar candidaturas"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listCandidaciesByStatus(statusCode: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listCandidaciesByStatus(statusCode, limit, offset)
            val list = items.map { it.toDomain() }
            emit(ResultWrapper.Success(list))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar candidaturas por status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateCandidacy(id: String, applicationDate: LocalDate?, statusId: String?, observations: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateCandidacy(id, applicationDate, statusId, observations)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateCandidacyStatus(id: String, statusId: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateCandidacyStatus(id, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteCandidacy(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteCandidacy(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao eliminar candidatura"))
        }
    }.flowOn(Dispatchers.IO)

    override fun countCandidaciesByStatus(statusCode: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val count = dataSource.countCandidaciesByStatus(statusCode)
            emit(ResultWrapper.Success(count))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao contar candidaturas"))
        }
    }.flowOn(Dispatchers.IO)

    // Extension Mappers para facilitar
    private fun com.google.firebase.dataconnect.generated.ListCandidaciesQuery.Data.CandidaciesItem.toDomain() = Candidacy(
        id = id.toString(),
        applicationDate = formatDataConnectDate(applicationDate),
        status = status?.let { CandidacyStatus(it.id.toString(), it.code, it.description, it.color) },
        observations = observations,
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun com.google.firebase.dataconnect.generated.ListCandidaciesByStatusQuery.Data.CandidaciesItem.toDomain() = Candidacy(
        id = id.toString(),
        applicationDate = formatDataConnectDate(applicationDate),
        status = status?.let { CandidacyStatus(it.id.toString(), it.code, it.description, it.color) },
        observations = observations,
        createdAt = formatTimestamp(createdAt),
        updatedAt = formatTimestamp(updatedAt)
    )

    private fun formatDataConnectDate(date: com.google.firebase.dataconnect.LocalDate): String {
        // Formata para DD/MM/YYYY
        return "${date.day.toString().padStart(2, '0')}/${date.month.toString().padStart(2, '0')}/${date.year}"
    }


    private fun formatTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}