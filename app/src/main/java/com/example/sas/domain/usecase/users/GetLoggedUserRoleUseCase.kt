package com.example.sas.domain.usecase.auth

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.UsersRepository
import com.example.sas.domain.usecase.users.GetUserByUidUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * UseCase que encapsula a descoberta da role do utilizador logado no sistema.
 */
class GetLoggedUserRoleUseCase @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getUserByUidUseCase: GetUserByUidUseCase
) {
    fun execute(): Flow<ResultWrapper<String?>> = flow {
        emit(ResultWrapper.Loading())

        getCurrentUserUseCase.execute().collect { authResult ->
            when (authResult) {
                is ResultWrapper.Success -> {
                    val uid = authResult.data?.uid
                    if (uid != null) {
                        getUserByUidUseCase.execute(uid).collect { userResult ->
                            when (userResult) {
                                is ResultWrapper.Success -> {
                                    emit(ResultWrapper.Success(userResult.data?.role))
                                }
                                is ResultWrapper.Error -> {
                                    emit(ResultWrapper.Error(userResult.message ?: "Erro ao buscar role"))
                                }
                                is ResultWrapper.Loading -> emit(ResultWrapper.Loading())
                            }
                        }
                    } else {
                        emit(ResultWrapper.Success(null)) // Sem utilizador logado
                    }
                }
                is ResultWrapper.Error -> {
                    emit(ResultWrapper.Error(authResult.message ?: "Erro de autenticação"))
                }
                is ResultWrapper.Loading -> emit(ResultWrapper.Loading())
            }
        }
    }
}