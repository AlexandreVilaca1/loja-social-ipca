package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    /**
     * Authenticates user with email and password.
     */
    fun authenticate(email: String, password: String): Flow<ResultWrapper<Unit>>

    /**
     * Gets current authenticated user from database.
     */
    fun getCurrentUser(): Flow<ResultWrapper<User>>

    /**
     * Checks if user is authenticated.
     */
    fun isAuthenticated(): Boolean

    /**
     * Signs out current user.
     */
    fun signOut(): Flow<ResultWrapper<Unit>>
}