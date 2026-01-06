package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.repositories.BeneficiariesRepository
import javax.inject.Inject

/**
 * Caso de uso para obter os detalhes completos de um beneficiário pelo seu ID único.
 */
class GetBeneficiaryByIdUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {
    fun execute(id: String) = repository.getBeneficiaryById(id)
}