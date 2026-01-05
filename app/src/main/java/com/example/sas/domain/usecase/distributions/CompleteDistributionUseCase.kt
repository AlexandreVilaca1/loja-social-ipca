package com.example.sas.domain.usecase.distributions

import android.util.Log
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionItemsRepository
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Use case for reducing lot quantities when a distribution is completed.
 */
class CompleteDistributionUseCase @Inject constructor(
    private val distributionItemsRepository: DistributionItemsRepository,
    private val lotsRepository: LotsRepository
) {
    companion object {
        private const val TAG = "CompleteDistributionUC"
    }

    /**
     * Executes the use case to reduce lot quantities for a distribution.
     * This will:
     * 1. Get all distribution items
     * 2. For each item, reduce the quantity of the lot by the distributed quantity
     *
     * Note: This use case does NOT update the distribution status.
     * The status should be updated separately before calling this.
     *
     * @param distributionId UUID of the distribution
     * @param statusId UUID of the ENTREGUE status (kept for backward compatibility, not used)
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        statusId: String
    ): Flow<ResultWrapper<Unit>> = flow {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(statusId.isNotBlank()) { "ID do status é obrigatório" }

        emit(ResultWrapper.Loading())

        try {
            // 1. Get all distribution items
            val items = mutableListOf<com.example.sas.domain.models.DistributionItem>()
            var hasError = false
            var errorMessage: String? = null

            distributionItemsRepository.listItemsByDistribution(
                distributionId = distributionId,
                limit = 100,
                offset = 0
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        // Continue collecting
                    }
                    is ResultWrapper.Success -> {
                        items.addAll(result.data ?: emptyList())
                        return@collect
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Error loading distribution items: ${result.message}")
                        hasError = true
                        errorMessage = result.message
                        return@collect
                    }
                }
            }

            if (hasError) {
                emit(ResultWrapper.Error(errorMessage ?: "Erro ao buscar itens da distribuição"))
                return@flow
            }

            // 2. For each item, get the lot and reduce its quantity
            for (item in items) {
                try {
                    // Get current lot information
                    var lot: com.example.sas.domain.models.Lot? = null
                    var lotError: String? = null

                    lotsRepository.getLotById(item.lotId).collect { lotResult ->
                        when (lotResult) {
                            is ResultWrapper.Loading -> {
                                // Continue collecting
                            }
                            is ResultWrapper.Success -> {
                                lot = lotResult.data
                                return@collect
                            }
                            is ResultWrapper.Error -> {
                                lotError = lotResult.message
                                return@collect
                            }
                        }
                    }

                    if (lotError != null) {
                        Log.e(TAG, "Error fetching lot: $lotError")
                        emit(ResultWrapper.Error(lotError ?: "Erro ao buscar lote"))
                        return@flow
                    }

                    if (lot != null) {
                        val newQuantity = lot!!.currentQuantity - item.quantity

                        if (newQuantity < 0) {
                            emit(ResultWrapper.Error(
                                "Quantidade insuficiente no lote ${item.lotCode}. " +
                                "Disponível: ${lot!!.currentQuantity}, Necessário: ${item.quantity}"
                            ))
                            return@flow
                        }

                        var updateError: String? = null

                        lotsRepository.updateLotQuantity(
                            id = item.lotId,
                            currentQuantity = newQuantity
                        ).collect { updateResult ->
                            when (updateResult) {
                                is ResultWrapper.Loading -> {
                                    // Continue collecting
                                }
                                is ResultWrapper.Success -> {
                                    return@collect
                                }
                                is ResultWrapper.Error -> {
                                    updateError = updateResult.message
                                    return@collect
                                }
                            }
                        }

                        if (updateError != null) {
                            Log.e(TAG, "Error updating lot quantity: $updateError")
                            emit(ResultWrapper.Error(updateError ?: "Erro ao atualizar quantidade do lote"))
                            return@flow
                        }
                    } else {
                        Log.e(TAG, "Lot ${item.lotId} not found")
                        emit(ResultWrapper.Error("Lote não encontrado: ${item.lotCode}"))
                        return@flow
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Exception processing lot ${item.lotId}: ${e.message}", e)
                    emit(ResultWrapper.Error("Erro ao processar lote ${item.lotCode}: ${e.message}"))
                    return@flow
                }
            }

            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            Log.e(TAG, "Exception completing distribution: ${e.message}", e)
            emit(ResultWrapper.Error(e.message ?: "Erro ao completar distribuição"))
        }
    }
}

