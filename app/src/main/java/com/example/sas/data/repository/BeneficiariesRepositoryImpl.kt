package com.example.sas.data.repository

import com.example.sas.data.datasource.BeneficiariesDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override fun createBeneficiary(
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String?,
        observations: String?
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
}

