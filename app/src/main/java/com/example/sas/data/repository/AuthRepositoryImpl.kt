package com.example.sas.data.repository

import com.example.sas.data.datasource.AuthDataSource
import com.example.sas.data.datasource.UsersDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val usersDataSource: UsersDataSource
) : AuthRepository {

    override fun authenticate(email: String, password: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            authDataSource.authenticate(email, password)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao autenticar"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCurrentUser(): Flow<ResultWrapper<User>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val uid = authDataSource.getCurrentUserUid()

            if (uid == null) {
                emit(ResultWrapper.Error("Utilizador não autenticado"))
                return@flow
            }

            // Fetch user from database
            val userFromDb = usersDataSource.getUserByUid(uid)

            if (userFromDb != null) {
                val user = User(
                    id = userFromDb.id.toString(),
                    name = userFromDb.name,
                    uid = userFromDb.uid,
                    email = userFromDb.email,
                    role = userFromDb.role,
                    createdAt = userFromDb.createdAt.toString(),
                    updatedAt = userFromDb.updatedAt.toString()
                )
                emit(ResultWrapper.Success(user))
            } else {
                emit(ResultWrapper.Error("Dados do utilizador não encontrados"))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao obter utilizador"))
        }
    }.flowOn(Dispatchers.IO)

    override fun isAuthenticated(): Boolean {
        return authDataSource.isAuthenticated()
    }

    override fun signOut(): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            authDataSource.signOut()
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao terminar sessão"))
        }
    }.flowOn(Dispatchers.IO)
}