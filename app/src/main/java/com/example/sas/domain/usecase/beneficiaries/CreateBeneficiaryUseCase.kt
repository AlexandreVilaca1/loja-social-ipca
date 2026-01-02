package com.example.sas.domain.usecase.beneficiaries

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.BeneficiariesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for creating a beneficiary.
 * Encapsulates business logic and validation.
 */
class CreateBeneficiaryUseCase @Inject constructor(
    private val repository: BeneficiariesRepository
) {

    /**
     * Creates a beneficiary with the given data.
     * @return Flow with result wrapper containing the UUID of created beneficiary
     * @throws IllegalArgumentException if required fields are missing/invalid
     */
    fun execute(
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String? = null,
        observations: String? = null
    ): Flow<ResultWrapper<String>> {
        // Business validation
        require(fullName.isNotBlank()) { "Nome completo é obrigatório" }
        require(studentNumber > 0) { "Número de aluno deve ser válido" }
        require(nif.isNotBlank()) { "NIF é obrigatório" }
        require(course.isNotBlank()) { "Curso é obrigatório" }
        require(contactNumber.isNotBlank()) { "Contacto é obrigatório" }

        return repository.createBeneficiary(
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

