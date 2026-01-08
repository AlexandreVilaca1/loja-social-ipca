package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import kotlinx.coroutines.flow.Flow

interface RequestsRepository {
    fun listRequests(limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>>
    fun getRequestById(id: String): Flow<ResultWrapper<Request>>
    fun createRequest(request: CreateRequestRequest): Flow<ResultWrapper<String>>
    fun updateRequest(request: UpdateRequestRequest): Flow<ResultWrapper<Unit>>
    fun updateRequestStatus(id: String, statusId: String): Flow<ResultWrapper<Unit>>
    fun deleteRequest(id: String): Flow<ResultWrapper<Unit>>
    fun searchRequests(term: String?, limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>>
    fun listRequestsByBeneficiary(beneficiaryId: String, limit: Int, offset: Int): Flow<ResultWrapper<List<Request>>>
}