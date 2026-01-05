package com.example.sas.domain.usecase.users

import com.example.sas.domain.repositories.UsersRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: UsersRepository) {
    fun execute(id: String) = repository.deleteUser(id)
}