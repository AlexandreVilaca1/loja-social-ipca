package com.example.sas.data.repository

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
                    item.responsibleStaff?.name, item.status?.code, item.status?.description,
                    item.createdAt?.toString())
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições"))
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
                    item.responsibleStaff?.name, item.status?.code, item.status?.description,
                    item.createdAt?.toString())
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições por status"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Helper function to map data source item to domain Distribution model.
     * Reduces code duplication.
     */
    private fun mapToDistribution(
        id: String,
        distributionDate: com.google.firebase.dataconnect.LocalDate,
        observations: String?,
        responsibleStaffName: String?,
        statusCode: String?,
        statusDescription: String?,
        createdAt: String?
    ): Distribution {
        val dateStr = distributionDate.toString()
        val formattedDate = try {
            "${distributionDate.year}/${String.format("%02d", distributionDate.month)}/${String.format("%02d", distributionDate.day)}"
        } catch (e: Exception) {
            dateStr // fallback to original if parsing fails
        }

        return Distribution(
            id = id,
            distributionDate = formattedDate,
            observations = observations,
            responsibleStaffName = responsibleStaffName,
            statusCode = statusCode,
            statusDescription = statusDescription,
            createdAt = createdAt
        )
    }
}