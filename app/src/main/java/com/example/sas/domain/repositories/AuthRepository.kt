package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun  login(email: String, password: String): Flow<ResultWrapper<Unit>>
}