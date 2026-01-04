package com.example.sas.domain.usecase.users

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.repositories.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing all users.
 */
class ListUsersUseCase @Inject constructor(
    private val repository: UsersRepository
) {

    /**
     * Executes the use case to list all users.
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of users
     */
    fun execute(
        limit: Int = 100,
        offset: Int = 0
    ): Flow<ResultWrapper<List<User>>> {
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }

        return repository.listUsers(limit, offset)
    }
}

