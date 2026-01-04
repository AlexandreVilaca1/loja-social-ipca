package com.example.sas.domain.usecase.status

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.repositories.StatusTypesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for getting a status type by code.
 */
class GetStatusTypeByCodeUseCase @Inject constructor(
    private val repository: StatusTypesRepository
) {

    /**
     * Executes the use case to get a status type by its code.
     * @param code Status code (e.g., "POR_ENTREGAR", "ENTREGUE")
     * @return Flow with result wrapper containing the status type
     */
    fun execute(code: String): Flow<ResultWrapper<StatusType?>> {
        require(code.isNotBlank()) { "Código do status é obrigatório" }
        return repository.getStatusTypeByCode(code)
    }
}

