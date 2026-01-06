package com.example.sas.data.repository

import com.example.sas.data.datasource.BeneficiariesDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.repositories.BeneficiariesRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of BeneficiariesRepository.
 * Handles data operations and error handling for beneficiaries.
 * Converts Firebase Data Connect types to domain models.
 */
@Singleton
class BeneficiariesRepositoryImpl @Inject constructor(
    private val dataSource: BeneficiariesDataSource
) : BeneficiariesRepository {

    override fun getBeneficiaryById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getBeneficiaryById(id)
            val beneficiary = item?.let {
                Beneficiary(
                    id = it.id.toString(),
                    fullName = it.fullName,
                    studentNumber = it.studentNumer, // Mapeado de studentNumer para studentNumber
                    nif = it.nif,
                    course = it.course,
                    isActive = it.isActive,
                    contactNumber = it.contactNumber,
                    uid = it.uid,
                    address = it.address,
                    observations = it.observations,
                    createdAt = formatTimestamp(it.createdAt)
                )
            }
            emit(ResultWrapper.Success(beneficiary))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar beneficiário"))
        }
    }.flowOn(Dispatchers.IO)

    // com.example.sas.data.repository.BeneficiariesRepositoryImpl.kt

    override fun getBeneficiaryByUserId(uid: String): Flow<ResultWrapper<Beneficiary?>> = flow {
        emit(ResultWrapper.Loading())
        try {
            // 1. Obter o item do DataSource
            val item = dataSource.getBeneficiaryByUserId(uid)

            val beneficiary = if (item != null) {
                Beneficiary(
                    id = item.id.toString(),
                    fullName = item.fullName,
                    studentNumber = item.studentNumer, // Campo do GQL (com erro de digitação original)
                    nif = item.nif,
                    course = item.course,
                    isActive = item.isActive,
                    contactNumber = item.contactNumber,
                    uid = item.uid,
                    address = item.address,
                    observations = item.observations,
                    createdAt = formatTimestamp(item.createdAt)
                )
            } else {
                null
            }

            emit(ResultWrapper.Success(beneficiary))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar beneficiário por UID"))
        }
    }.flowOn(Dispatchers.IO)


    override fun listBeneficiaries(limit: Int, offset: Int): Flow<ResultWrapper<List<Beneficiary>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listBeneficiaries(limit, offset)

            // Map Firebase Data Connect items to domain models
            val beneficiaries = items.map { item ->
                Beneficiary(
                    id = item.id.toString(),
                    fullName = item.fullName,
                    studentNumber = item.studentNumer,
                    nif = item.nif,
                    course = item.course,
                    isActive = item.isActive,
                    contactNumber = item.contactNumber,
                    address = item.address,
                    observations = item.observations,
                    createdAt = item.createdAt?.toString()
                )
            }

            emit(ResultWrapper.Success(beneficiaries))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar beneficiários"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchBeneficiaries(searchTerm: String, limit: Int, offset: Int): Flow<ResultWrapper<List<Beneficiary>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchBeneficiaries(searchTerm, limit, offset)

            // Map Firebase Data Connect items to domain models
            val beneficiaries = items.map { item ->
                Beneficiary(
                    id = item.id.toString(),
                    fullName = item.fullName,
                    studentNumber = item.studentNumer,
                    nif = item.nif,
                    course = item.course,
                    isActive = item.isActive,
                    contactNumber = item.contactNumber,
                    address = item.address,
                    observations = item.observations,
                    createdAt = item.createdAt?.toString()
                )
            }

            emit(ResultWrapper.Success(beneficiaries))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao pesquisar beneficiários"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createBeneficiary(
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String?,
        observations: String?,
        uid: String?,
        ): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createBeneficiary(
                fullName = fullName,
                studentNumber = studentNumber,
                nif = nif,
                course = course,
                isActive = isActive,
                contactNumber = contactNumber,
                address = address,
                observations = observations
            )
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar beneficiário"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateBeneficiary(
        id: String,
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String?,
        observations: String?,
        uid : String?
    ): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateBeneficiary(
                id = id,
                fullName = fullName,
                studentNumber = studentNumber,
                nif = nif,
                course = course,
                isActive = isActive,
                contactNumber = contactNumber,
                address = address,
                observations = observations,
                uid = uid
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar beneficiário"))
        }
    }.flowOn(Dispatchers.IO)

    private fun formatTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}


