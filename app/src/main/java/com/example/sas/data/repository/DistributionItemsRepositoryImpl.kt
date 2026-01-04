package com.example.sas.data.repository

import com.example.sas.data.datasource.DistributionItemsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.DistributionItem
import com.example.sas.domain.repositories.DistributionItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of DistributionItemsRepository.
 */
@Singleton
class DistributionItemsRepositoryImpl @Inject constructor(
    private val dataSource: DistributionItemsDataSource
) : DistributionItemsRepository {

    override fun listItemsByDistribution(
        distributionId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<DistributionItem>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listItemsByDistribution(distributionId, limit, offset)

            // Map Firebase Data Connect items to domain models
            val distributionItems = items.map { item ->
                DistributionItem(
                    lotCode = item.lot.lotCode,
                    productName = item.lot.product?.name ?: "Produto desconhecido",
                    productUnit = item.lot.product?.unitOfMeasure ?: "",
                    quantity = item.quantity,
                    observations = item.observations
                )
            }

            emit(ResultWrapper.Success(distributionItems))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar itens da distribuição"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createDistributionItem(
        distributionId: String,
        lotId: String,
        quantity: Int,
        observations: String?
    ): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.createDistributionItem(distributionId, lotId, quantity, observations)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar item da distribuição"))
        }
    }.flowOn(Dispatchers.IO)
}

