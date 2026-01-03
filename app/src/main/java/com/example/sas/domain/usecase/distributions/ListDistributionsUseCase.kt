package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing all distributions.
 */
class ListDistributionsUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to list all distributions.
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distributions
     */
    fun execute(
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Distribution>>> {
        // Business validation
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listDistributions(limit, offset)
    }
}