package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Request
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListRequestsUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(limit: Int = 20, offset: Int = 0): Flow<ResultWrapper<List<Request>>> =
        repository.listRequests(limit, offset)
}