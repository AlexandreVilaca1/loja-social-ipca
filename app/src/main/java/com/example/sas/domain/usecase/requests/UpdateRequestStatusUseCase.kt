package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRequestStatusUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(id: String, statusId: String): Flow<ResultWrapper<Unit>> =
        repository.updateRequestStatus(id, statusId)
}