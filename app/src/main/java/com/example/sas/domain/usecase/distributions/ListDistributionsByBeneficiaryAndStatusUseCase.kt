package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing distributions by beneficiary and status.
 */
class ListDistributionsByBeneficiaryAndStatusUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to list distributions for a beneficiary filtered by status.
     * @param beneficiaryId UUID of the beneficiary
     * @param statusCode Status code to filter by
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of distributions
     */
    fun execute(
        beneficiaryId: String,
        statusCode: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Distribution>>> {
        // Business validation
        require(beneficiaryId.isNotBlank()) { "ID do beneficiário é obrigatório" }
        require(statusCode.isNotBlank()) { "Código do status é obrigatório" }
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listDistributionsByBeneficiaryAndStatus(beneficiaryId, statusCode, limit, offset)
    }
}

