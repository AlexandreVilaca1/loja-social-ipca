package com.example.sas.domain.usecase.distributionItems

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for deleting a distribution item.
 */
class DeleteDistributionItemUseCase @Inject constructor(
    private val repository: DistributionItemsRepository
) {

    /**
     * Executes the use case to delete a distribution item.
     * @param distributionId UUID of the distribution
     * @param lotId UUID of the lot
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        lotId: String
    ): Flow<ResultWrapper<Unit>> {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(lotId.isNotBlank()) { "ID do lote é obrigatório" }

        return repository.deleteDistributionItem(distributionId, lotId)
    }
}

