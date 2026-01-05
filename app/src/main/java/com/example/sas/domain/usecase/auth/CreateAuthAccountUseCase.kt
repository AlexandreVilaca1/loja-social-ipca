package com.example.sas.domain.usecase.auth

import android.content.Context
import com.example.sas.domain.repositories.AuthRepository
import javax.inject.Inject

/**
 * Caso de uso para criar uma conta de autenticação no Firebase sem desconectar o admin.
 * Requer o Context para inicializar uma instância temporária do Firebase.
 */
class CreateAuthAccountUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    fun execute(context: Context, email: String, password: String) =
        repository.createAccount(context, email, password)
}