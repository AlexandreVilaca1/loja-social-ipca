package com.example.sas.domain.usecase.distributionItems

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.DistributionItem
import com.example.sas.domain.repositories.DistributionItemsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing distribution items by distribution.
 */
class ListDistributionItemsUseCase @Inject constructor(
    private val repository: DistributionItemsRepository
) {

    /**
     * Executes the use case to list items for a distribution.
     * @param distributionId UUID of the distribution
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distribution items
     */
    fun execute(
        distributionId: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<DistributionItem>>> {
        // Business validation
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listItemsByDistribution(distributionId, limit, offset)
    }
}

