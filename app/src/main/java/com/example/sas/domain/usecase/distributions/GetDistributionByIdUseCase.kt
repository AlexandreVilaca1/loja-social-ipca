package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting a distribution by ID.
 */
class GetDistributionByIdUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to get a distribution by its ID.
     * @param distributionId UUID of the distribution
     * @return Flow with result wrapper containing the distribution
     */
    fun execute(distributionId: String): Flow<ResultWrapper<Distribution?>> {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        return repository.getDistributionById(distributionId)
    }
}

