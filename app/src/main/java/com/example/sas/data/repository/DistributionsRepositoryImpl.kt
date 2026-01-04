package com.example.sas.data.repository

import android.annotation.SuppressLint
import com.example.sas.data.datasource.DistributionsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of DistributionsRepository.
 */
@Singleton
class DistributionsRepositoryImpl @Inject constructor(
    private val dataSource: DistributionsDataSource
) : DistributionsRepository {

    override fun getDistributionById(distributionId: String): Flow<ResultWrapper<Distribution?>> = flow<ResultWrapper<Distribution?>> {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getDistributionById(distributionId)
            if (item != null) {
                val distribution = mapToDistribution(
                    item.id.toString(),
                    item.distributionDate,
                    item.observations,
                    item.responsibleStaff?.name,
                    item.beneficiary?.fullName,
                    item.status?.code,
                    item.status?.description,
                    item.createdAt?.toString()
                )
                emit(ResultWrapper.Success(distribution))
            } else {
                emit(ResultWrapper.Success(null))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar distribuição"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listDistributions(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDistributions(limit, offset)
            val distributions = items.map { item ->
                mapToDistribution(
                    item.id.toString(),
                    item.distributionDate,
                    item.observations,
                    item.responsibleStaff?.name,
                    item.beneficiary?.fullName,
                    item.status?.code,
                    item.status?.description,
                    item.createdAt?.toString()
                )
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listDistributionsByBeneficiary(
        beneficiaryId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDistributionsByBeneficiary(beneficiaryId, limit, offset)
            val distributions = items.map { item ->
                mapToDistribution(item.id.toString(), item.distributionDate, item.observations,
                    item.responsibleStaff?.name, null, item.status?.code, item.status?.description,
                    item.createdAt?.toString())
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listDistributionsByBeneficiaryAndStatus(
        beneficiaryId: String,
        statusCode: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDistributionsByBeneficiaryAndStatus(beneficiaryId, statusCode, limit, offset)
            val distributions = items.map { item ->
                mapToDistribution(item.id.toString(), item.distributionDate, item.observations,
                    item.responsibleStaff?.name, null, item.status?.code, item.status?.description,
                    item.createdAt?.toString())
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições por beneficiário e status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listDistributionsByStatus(
        statusCode: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDistributionsByStatus(statusCode, limit, offset)
            val distributions = items.map { item ->
                mapToDistribution(item.id.toString(), item.distributionDate, item.observations,
                    item.responsibleStaff?.name, item.beneficiary?.fullName, item.status?.code, item.status?.description,
                    item.createdAt?.toString())
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições por status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createDistribution(
        beneficiaryId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val distributionId = dataSource.createDistribution(
                beneficiaryId = beneficiaryId,
                distributionDate = distributionDate,
                responsibleStaffId = responsibleStaffId,
                statusId = statusId,
                observations = observations
            )
            emit(ResultWrapper.Success(distributionId))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar distribuição"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateDistributionStatus(
        distributionId: String,
        statusId: String
    ): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateDistributionStatus(distributionId, statusId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar status da distribuição"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateDistribution(
        distributionId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateDistribution(
                distributionId = distributionId,
                distributionDate = distributionDate,
                responsibleStaffId = responsibleStaffId,
                statusId = statusId,
                observations = observations
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar distribuição"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Helper function to map data source item to domain Distribution model.
     * Reduces code duplication.
     */
    @SuppressLint("DefaultLocale")
    private fun mapToDistribution(
        id: String,
        distributionDate: com.google.firebase.dataconnect.LocalDate,
        observations: String?,
        responsibleStaffName: String?,
        beneficiaryName: String?,
        statusCode: String?,
        statusDescription: String?,
        createdAt: String?
    ): Distribution {
        val dateStr = distributionDate.toString()
        val formattedDate = try {
            "${distributionDate.year}/${String.format("%02d", distributionDate.month)}/${String.format("%02d", distributionDate.day)}"
        } catch (_: Exception) {
            dateStr // fallback to original if parsing fails
        }

        return Distribution(
            id = id,
            distributionDate = formattedDate,
            observations = observations,
            responsibleStaffName = responsibleStaffName,
            beneficiaryName = beneficiaryName,
            statusCode = statusCode,
            statusDescription = statusDescription,
            createdAt = createdAt
        )
    }
}