package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for updating a beneficiary.
 * Encapsulates business logic and validation.
 */
class UpdateBeneficiaryUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {

    /**
     * Updates a beneficiary with the given data.
     * @return Flow with result wrapper containing Unit on success
     * @throws IllegalArgumentException if required fields are missing/invalid
     */
    fun execute(
        id: String,
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String? = null,
        observations: String? = null
    ): Flow<ResultWrapper<Unit>> {
        // Business validation
        require(id.isNotBlank()) { "ID é obrigatório" }
        require(fullName.isNotBlank()) { "Nome completo é obrigatório" }
        require(studentNumber > 0) { "Número de aluno deve ser válido" }
        require(nif.isNotBlank()) { "NIF é obrigatório" }
        require(course.isNotBlank()) { "Curso é obrigatório" }
        require(contactNumber.isNotBlank()) { "Contacto é obrigatório" }

        return repository.updateBeneficiary(
            id = id,
            fullName = fullName,
            studentNumber = studentNumber,
            nif = nif,
            course = course,
            isActive = isActive,
            contactNumber = contactNumber,
            address = address,
            observations = observations
        )
    }
}


