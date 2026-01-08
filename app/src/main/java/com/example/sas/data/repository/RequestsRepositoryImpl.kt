package com.example.sas.data.repository

import com.example.sas.data.datasource.RequestsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.repositories.RequestsRepository
import com.google.firebase.dataconnect.LocalDate
import com.google.firebase.dataconnect.generated.GetRequestByIdQuery
import com.google.firebase.dataconnect.generated.ListRequestsByBeneficiaryQuery
import com.google.firebase.dataconnect.generated.ListRequestsQuery
import com.google.firebase.dataconnect.generated.SearchRequestsQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestsRepositoryImpl @Inject constructor(
    private val dataSource: RequestsDataSource
) : RequestsRepository {

    override fun listRequests(limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listRequests(limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar pedidos"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getRequestById(id: String): Flow<ResultWrapper<Request>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getRequestById(id)
            if (item != null) emit(ResultWrapper.Success(item.toDomain()))
            else emit(ResultWrapper.Error("Pedido não encontrado"))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar pedido"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createRequest(request: CreateRequestRequest): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createRequest(
                beneficiaryId = request.beneficiaryId,
                requestDate = parseDate(request.requestDate),
                description = request.description,
                statusId = request.statusId
            )
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar pedido"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateRequest(request: UpdateRequestRequest): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateRequest(
                id = request.id,
                requestDate = request.requestDate?.let { parseDate(it) },
                description = request.description,
                statusId = request.statusId
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar pedido"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateRequestStatus(id: String, statusId: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateRequestStatus(id, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteRequest(id: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteRequest(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao eliminar pedido"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchRequests(term: String?, limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchRequests(term, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro na pesquisa"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listRequestsByBeneficiary(beneficiaryId: String, limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listRequestsByBeneficiary(beneficiaryId, limit, offset)
            emit(ResultWrapper.Success(items.map { it.toDomain() }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar pedidos do beneficiário"))
        }
    }.flowOn(Dispatchers.IO)

    // --- Mappers e Helpers ---

    private fun formatDate(date: LocalDate): String =
        "${date.year}-${String.format("%02d", date.month)}-${String.format("%02d", date.day)}"

    private fun parseDate(dateStr: String): LocalDate {
        val parts = dateStr.replace("/", "-").split("-")
        return LocalDate(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }

    private fun GetRequestByIdQuery.Data.Request.toDomain() = Request(
        id = id.toString(),
        beneficiary = Beneficiary(
            id = beneficiary.id.toString(),
            fullName = beneficiary.fullName,
            studentNumber = beneficiary.studentNumer,
            nif = beneficiary.nif,
            course = beneficiary.course,
            isActive = beneficiary.isActive,
            contactNumber = beneficiary.contactNumber,
            address = beneficiary.address,
            observations = beneficiary.observations
        ),
        requestDate = formatDate(requestDate),
        description = description,
        status = status?.let { StatusType(it.id.toString(), it.code, it.description, it.color) },
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )


    private fun ListRequestsQuery.Data.RequestsItem.toDomain() = Request(
        id = id.toString(),
        beneficiary = Beneficiary(
            id = beneficiary.id.toString(),
            fullName = beneficiary.fullName,
            studentNumber = beneficiary.studentNumer,
            nif = beneficiary.nif,
            course = beneficiary.course,
            isActive = true, // Default se não vier na query de lista
            contactNumber = beneficiary.contactNumber,
            address = null,
            observations = null
        ),
        requestDate = formatDate(requestDate),
        description = description,
        status = status?.let { StatusType(it.id.toString(), it.code, it.description, it.color) },
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )

    // Extensões similares para ListRequestsByBeneficiaryQuery e SearchRequestsQuery...
    private fun ListRequestsByBeneficiaryQuery.Data.RequestsItem.toDomain() = Request(
        id = id.toString(),
        beneficiary = Beneficiary(
            id = beneficiary.id.toString(),
            fullName = beneficiary.fullName,
            studentNumber = beneficiary.studentNumer,
            nif = "", course = "", isActive = true, contactNumber = "" // Campos mínimos para lista
        ),
        requestDate = formatDate(requestDate),
        description = description,
        status = status?.let { StatusType(it.id.toString(), it.code, it.description, it.color) },
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )

    private fun SearchRequestsQuery.Data.RequestsItem.toDomain() = Request(
        id = id.toString(),
        beneficiary = Beneficiary(
            id = beneficiary.id.toString(),
            fullName = beneficiary.fullName,
            studentNumber = beneficiary.studentNumer,
            nif = beneficiary.nif,
            course = "",
            isActive = true,
            contactNumber = beneficiary.contactNumber
        ),
        requestDate = formatDate(requestDate),
        description = description,
        status = status?.let { StatusType(it.id.toString(), it.code, it.description, it.color) },
        createdAt = createdAt.toString(),
        updatedAt = updatedAt.toString()
    )
}