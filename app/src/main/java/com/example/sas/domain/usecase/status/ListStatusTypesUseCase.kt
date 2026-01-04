package com.example.sas.domain.usecase.status

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.repositories.StatusTypesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing all status types.
 */
class ListStatusTypesUseCase @Inject constructor(
    private val repository: StatusTypesRepository
) {

    /**
     * Executes the use case to list all status types.
     * @return Flow with result wrapper containing list of status types
     */
    fun execute(): Flow<ResultWrapper<List<StatusType>>> {
        return repository.listStatusTypes()
    }
}

