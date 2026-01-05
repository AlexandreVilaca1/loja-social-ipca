package com.example.sas.domain.usecase.auth

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun execute(): Flow<ResultWrapper<com.example.sas.domain.models.User>> {
        return repository.getCurrentUser()
    }
}