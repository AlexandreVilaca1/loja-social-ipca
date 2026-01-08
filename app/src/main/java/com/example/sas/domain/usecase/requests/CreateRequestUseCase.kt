package com.example.sas.domain.usecase.requests

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateRequestRequest
import com.example.sas.domain.repositories.RequestsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateRequestUseCase @Inject constructor(
    private val repository: RequestsRepository
) {
    fun execute(request: CreateRequestRequest): Flow<ResultWrapper<String>> {
        require(request.beneficiaryId.isNotBlank()) { "Beneficiário é obrigatório" }
        require(request.requestDate.isNotBlank()) { "Data é obrigatória" }
        return repository.createRequest(request)
    }
}