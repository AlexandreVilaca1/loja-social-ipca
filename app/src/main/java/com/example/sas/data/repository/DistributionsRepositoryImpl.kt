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
    override fun listDistributionsByBeneficiary(
        beneficiaryId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Distribution>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listDistributionsByBeneficiary(beneficiaryId, limit, offset)
            val distributions = items.map { item ->
                val dateStr = item.distributionDate.toString()
                val formattedDate = try {
                    val date = item.distributionDate
                    "${date.year}/${String.format("%02d", date.month)}/${String.format("%02d", date.day)}"
                } catch (e: Exception) {
                    dateStr // fallback to original if parsing fails
                }

                Distribution(
                    id = item.id.toString(),
                    distributionDate = formattedDate,
                    observations = item.observations,
                    responsibleStaffName = item.responsibleStaff?.name,
                    statusCode = item.status?.code,
                    statusDescription = item.status?.description,
                    createdAt = item.createdAt?.toString()
                )
            }
            emit(ResultWrapper.Success(distributions))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar distribuições"))
        }
    }.flowOn(Dispatchers.IO)
}
