package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteRequestUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(id: String): Flow<ResultWrapper<Unit>> = repository.deleteRequest(id)
}