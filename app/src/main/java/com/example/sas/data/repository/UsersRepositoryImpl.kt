package com.example.sas.data.repository

import com.example.sas.data.datasource.UsersDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.repositories.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of UsersRepository.
 */
@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val dataSource: UsersDataSource
) : UsersRepository {

    override fun listUsers(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<User>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listUsers(limit, offset)
            val users = items.map { item ->
                User(
                    id = item.id.toString(),
                    name = item.name,
                    email = item.email,
                    role = item.role,
                    uid = item.uid,
                    createdAt = item.createdAt.toString(),
                    updatedAt = item.updatedAt.toString()
                )
            }
            emit(ResultWrapper.Success(users))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar utilizadores"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getUserByUid(uid: String): Flow<ResultWrapper<User>> = flow {

        emit(ResultWrapper.Loading())

        try {
            val userDb = dataSource.getUserByUid(uid)

            if (userDb == null) {
                emit(ResultWrapper.Error("Utilizador não encontrado"))
            } else {

                val user = User(
                    id = userDb.id.toString(),
                    name = userDb.name,
                    uid = userDb.uid ?: "",
                    email = userDb.email,
                    role = userDb.role,
                    createdAt = userDb.createdAt.toString(),
                    updatedAt = userDb.updatedAt.toString()
                )

                emit(ResultWrapper.Success(user))
            }

        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao obter utilizador"))
        }

    }.flowOn(Dispatchers.IO)

}

