package com.example.sas.domain.usecase.auth

import com.example.sas.domain.repositories.AuthRepository
import javax.inject.Inject

class IsAuthenticatedUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun execute(): Boolean {
        return repository.isAuthenticated()
    }
}