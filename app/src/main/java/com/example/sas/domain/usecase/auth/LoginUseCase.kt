package com.example.sas.domain.usecase.auth

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<ResultWrapper<Unit>> {
        return repository.login(email, password)
    }
}