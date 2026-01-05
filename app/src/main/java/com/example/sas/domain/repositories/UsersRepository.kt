package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun listUsers(limit: Int?, offset: Int?): Flow<ResultWrapper<List<User>>>
    fun getUserByUid(uid: String): Flow<ResultWrapper<User?>>
    fun createUser(uid: String, name: String, email: String, passwordHash: String, role: String): Flow<ResultWrapper<String>>
    fun deleteUser(id: String): Flow<ResultWrapper<Unit>>
    fun updateUser(id: String, uid: String? = null, name: String? = null, email: String? = null, role: String? = null): Flow<ResultWrapper<Unit>>
}