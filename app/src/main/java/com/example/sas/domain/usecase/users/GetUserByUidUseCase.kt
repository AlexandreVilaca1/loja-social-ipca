package com.example.sas.domain.usecase.users

import com.example.sas.domain.repositories.UsersRepository
import javax.inject.Inject

class GetUserByUidUseCase @Inject constructor(private val repository: UsersRepository) {
    fun execute(uid: String) = repository.getUserByUid(uid)
}