package com.example.sas.presentation.ui.Login

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.auth.LoginUseCase
import com.example.sas.domain.usecase.auth.SignOutUseCase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String? = null,
    val password: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val showCandidacyForm: Boolean = false,
    val candFullName: String = "",
    val candStudentNumber: String = "",
    val candNif: String = "",
    val candCourse: String = "",
    val candContact: String = "",
    val candReason: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun setEmail(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess: () -> Unit) {

        // 1️⃣ Validação
        if (uiState.value.email.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(error = "Email is required")
            return
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(error = "Password is required")
            return
        }

        viewModelScope.launch {
            loginUseCase(
                uiState.value.email!!,
                uiState.value.password!!
            ).collect { result ->

                when (result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(
                            isLoading = true,
                            error = null
                        )
                    }

                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = null
                        )
                        onLoginSuccess()
                    }

                    is ResultWrapper.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun toggleCandidacyForm(show: Boolean) {
        uiState.value = uiState.value.copy(showCandidacyForm = show)
    }

    fun updateCandidacyField(
        name: String? = null,
        studentNum: String? = null,
        nif: String? = null,
        course: String? = null,
        contact: String? = null,
        reason: String? = null
    ) {
        uiState.value = uiState.value.copy(
            candFullName = name ?: uiState.value.candFullName,
            candStudentNumber = studentNum ?: uiState.value.candStudentNumber,
            candNif = nif ?: uiState.value.candNif,
            candCourse = course ?: uiState.value.candCourse,
            candContact = contact ?: uiState.value.candContact,
            candReason = reason ?: uiState.value.candReason
        )
    }

    fun sendCandidacyEmail(context: Context) {
        val state = uiState.value
        val emailDestino = "pedrotcalvess@gmail.com" // Email da instituição
        val assunto = "Nova Candidatura a Beneficiário - ${state.candFullName}"

        val corpoEmail = """
            Nome Completo: ${state.candFullName}
            Número de Aluno: ${state.candStudentNumber}
            NIF: ${state.candNif}
            Curso: ${state.candCourse}
            Contacto: ${state.candContact}
            
            Motivo da Candidatura:
            ${state.candReason}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailDestino))
            putExtra(Intent.EXTRA_SUBJECT, assunto)
            putExtra(Intent.EXTRA_TEXT, corpoEmail)
        }

        try {
            context.startActivity(Intent.createChooser(intent, "Enviar candidatura via..."))
            toggleCandidacyForm(false) // Fecha o formulário após enviar
        } catch (e: Exception) {
            uiState.value = uiState.value.copy(error = "Não foi possível abrir o app de email")
        }
    }
}
