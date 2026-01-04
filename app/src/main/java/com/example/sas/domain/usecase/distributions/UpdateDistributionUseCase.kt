package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for updating a distribution.
 */
class UpdateDistributionUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to update a distribution.
     * @param distributionId UUID of the distribution
     * @param distributionDate Date of the distribution (format: YYYY-MM-DD)
     * @param responsibleStaffId UUID of the responsible staff member
     * @param statusId UUID of the status
     * @param observations Optional observations
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String?
    ): Flow<ResultWrapper<Unit>> {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(distributionDate.isNotBlank()) { "Data da distribuição é obrigatória" }
        require(responsibleStaffId.isNotBlank()) { "ID do responsável é obrigatório" }
        require(statusId.isNotBlank()) { "ID do status é obrigatório" }

        return repository.updateDistribution(
            distributionId = distributionId,
            distributionDate = distributionDate,
            responsibleStaffId = responsibleStaffId,
            statusId = statusId,
            observations = observations
        )
    }
}

