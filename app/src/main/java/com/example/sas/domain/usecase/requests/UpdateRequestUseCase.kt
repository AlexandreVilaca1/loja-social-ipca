package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.UpdateRequestRequest
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateRequestUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(request: UpdateRequestRequest): Flow<ResultWrapper<Unit>> =
        repository.updateRequest(request)
}