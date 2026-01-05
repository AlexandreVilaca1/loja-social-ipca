package com.example.sas.domain.usecase.users

import com.example.sas.domain.repositories.UsersRepository
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(private val repository: UsersRepository) {
    fun execute(id: String, uid: String? = null, name: String? = null, email: String? = null, role: String? = null) =
        repository.updateUser(id, uid, name, email, role)
}