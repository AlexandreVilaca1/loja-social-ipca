package com.example.sas.domain.usecase.users

import com.example.sas.domain.repositories.UsersRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repository: UsersRepository) {
    fun execute(uid: String, name: String, email: String, passwordHash: String, role: String) =
        repository.createUser(uid, name, email, passwordHash, role)
}