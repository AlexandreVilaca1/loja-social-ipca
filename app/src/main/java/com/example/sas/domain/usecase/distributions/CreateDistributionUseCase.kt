package com.example.sas.domain.usecase.distributions

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.DistributionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for creating a new distribution.
 */
class CreateDistributionUseCase @Inject constructor(
    private val repository: DistributionsRepository
) {

    /**
     * Executes the use case to create a new distribution.
     * @param beneficiaryId UUID of the beneficiary
     * @param distributionDate Date of the distribution
     * @param responsibleStaffId UUID of the responsible staff member
     * @param statusId UUID of the status
     * @param observations Optional observations
     * @return Flow with result wrapper containing the created distribution ID
     */
    fun execute(
        beneficiaryId: String,
        distributionDate: String,
        responsibleStaffId: String,
        statusId: String,
        observations: String? = null
    ): Flow<ResultWrapper<String>> {
        // Business validation
        require(beneficiaryId.isNotBlank()) { "ID do beneficiário é obrigatório" }
        require(distributionDate.isNotBlank()) { "Data da distribuição é obrigatória" }
        require(responsibleStaffId.isNotBlank()) { "Funcionário responsável é obrigatório" }
        require(statusId.isNotBlank()) { "Status é obrigatório" }

        return repository.createDistribution(
            beneficiaryId = beneficiaryId,
            distributionDate = distributionDate,
            responsibleStaffId = responsibleStaffId,
            statusId = statusId,
            observations = observations
        )
    }
}

