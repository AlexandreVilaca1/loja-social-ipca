package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Request
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRequestsUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(term: String?, limit: Int = 20, offset: Int = 0): Flow<ResultWrapper<List<Request>>> =
        repository.searchRequests(term, limit, offset)
}