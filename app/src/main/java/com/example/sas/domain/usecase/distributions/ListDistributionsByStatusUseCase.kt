package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing distributions by status.
 */
class ListDistributionsByStatusUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to list distributions by status code.
     * @param statusCode Status code to filter by (e.g., "PENDING", "COMPLETED", "CANCELLED")
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distributions
     */
    fun execute(
        statusCode: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Distribution>>> {
        // Business validation
        require(statusCode.isNotBlank()) { "Código do status é obrigatório" }
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listDistributionsByStatus(statusCode, limit, offset)
    }
}