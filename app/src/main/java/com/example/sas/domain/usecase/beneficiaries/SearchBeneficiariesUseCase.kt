package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for searching beneficiaries.
 * Searches by name or NIF.
 */
class SearchBeneficiariesUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {

    /**
     * Executes the search for beneficiaries.
     * @param searchTerm Term to search (name or NIF)
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of beneficiaries
     */
    fun execute(
        searchTerm: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Beneficiary>>> {
        // Business validation
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        // If search term is empty, return empty flow or could call listBeneficiaries instead
        if (searchTerm.isBlank()) {
            return kotlinx.coroutines.flow.flowOf(ResultWrapper.Success(emptyList()))
        }

        return repository.searchBeneficiaries(searchTerm, limit, offset)
    }
}

