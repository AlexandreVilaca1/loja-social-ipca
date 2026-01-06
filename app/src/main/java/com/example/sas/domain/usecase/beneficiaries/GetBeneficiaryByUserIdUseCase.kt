package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para encontrar os dados de beneficiário associados a um UID
 * do Firebase Authentication.
 */
class GetBeneficiaryByUserIdUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {
    fun execute(uid: String): Flow<ResultWrapper<Beneficiary?>> {
        return repository.getBeneficiaryByUserId(uid)
    }
}