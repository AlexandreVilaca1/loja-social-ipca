package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for updating distribution status.
 */
class UpdateDistributionStatusUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to update distribution status.
     * @param distributionId UUID of the distribution
     * @param statusId UUID of the new status
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        statusId: String
    ): Flow<ResultWrapper<Unit>> {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(statusId.isNotBlank()) { "ID do status é obrigatório" }

        return repository.updateDistributionStatus(distributionId, statusId)
    }
}

