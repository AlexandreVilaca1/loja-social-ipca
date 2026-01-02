package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing beneficiaries.
 * Encapsulates business logic for retrieving beneficiaries list.
 */
class ListBeneficiariesUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {

    /**
     * Executes the use case to list beneficiaries.
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of beneficiaries
     */
    fun execute(
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Beneficiary>>> {
        // Business validation
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listBeneficiaries(limit, offset)
    }
}

