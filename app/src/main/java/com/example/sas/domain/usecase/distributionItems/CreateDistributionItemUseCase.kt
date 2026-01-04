package com.example.sas.domain.usecase.distributionItems

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for creating a distribution item.
 */
class CreateDistributionItemUseCase @Inject constructor(
    private val repository: DistributionItemsRepository
) {

    /**
     * Executes the use case to create a distribution item.
     * @param distributionId UUID of the distribution
     * @param lotId UUID of the lot
     * @param quantity Quantity to distribute
     * @param observations Optional observations
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        lotId: String,
        quantity: Int,
        observations: String?
    ): Flow<ResultWrapper<Unit>> {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(lotId.isNotBlank()) { "ID do lote é obrigatório" }
        require(quantity > 0) { "Quantidade deve ser maior que zero" }

        return repository.createDistributionItem(distributionId, lotId, quantity, observations)
    }
}

