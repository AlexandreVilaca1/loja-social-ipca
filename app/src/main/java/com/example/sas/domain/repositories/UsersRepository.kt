package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for users operations.
 */
interface UsersRepository {

    /**
     * Lists all users.
     */
    fun listUsers(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<User>>>
}

