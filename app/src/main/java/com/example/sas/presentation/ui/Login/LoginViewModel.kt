package com.example.sas.presentation.ui.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sas.domain.usecase.auth.LoginUseCase
import com.example.sas.domain.usecase.auth.SignOutUseCase
import com.example.sas.domain.usecase.candidacies.CreateCandidacyUseCase
import com.google.firebase.dataconnect.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
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
    val candReason: String = "",
    val isSubmittingCandidacy: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val createCandidacyUseCase: CreateCandidacyUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun setEmail(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess: (String) -> Unit) {
        if (uiState.value.email.isNullOrEmpty() || uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(error = "Email e Password são obrigatórios")
            return
        }

        viewModelScope.launch {
            // 1. Faz o Login no Firebase Auth
            loginUseCase(uiState.value.email!!, uiState.value.password!!).collect { authResult ->
                when (authResult) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(isLoading = true, error = null)
                    }

                    is ResultWrapper.Success -> {
                        // 2. Login de Auth sucesso! Agora buscamos a ROLE na base de dados
                        fetchUserRoleAndNavigate(onLoginSuccess)
                    }

                    is ResultWrapper.Error -> {
                        uiState.value =
                            uiState.value.copy(isLoading = false, error = authResult.message)
                    }
                }
            }
        }
    }

    private fun fetchUserRoleAndNavigate(onLoginSuccess: (String) -> Unit) {
        viewModelScope.launch {
            getCurrentUserUseCase.execute().collect { userResult ->
                when (userResult) {
                    is ResultWrapper.Success -> {
                        val role = userResult.data?.role ?: "BEN"
                        uiState.value = uiState.value.copy(isLoading = false)
                        onLoginSuccess(role)
                    }
                    is ResultWrapper.Error -> {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Erro ao carregar perfil: ${userResult.message}"
                        )
                    }
                    else -> {}
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

    fun submitCandidacy() {
        val state = uiState.value
        val pendenteID = "e0260af6-f4a2-4a7f-9f83-659e0c9b0385"

        // 1. Validação simples
        if (state.candFullName.isBlank() || state.candNif.isBlank()) {
            uiState.value = uiState.value.copy(error = "Nome e NIF são obrigatórios")
            return
        }

        // 2. Preparar a data (LocalDate do Data Connect)
        val calendar = Calendar.getInstance()
        val today = LocalDate(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH) + 1,
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // 3. Estruturar os dados do Beneficiário no campo observations
        val structuredObservations = """
            [DADOS DO BENEFICIÁRIO]
            Nome: ${state.candFullName}
            Nº Aluno: ${state.candStudentNumber}
            NIF: ${state.candNif}
            Curso: ${state.candCourse}
            Contacto: ${state.candContact}
            
            [MOTIVAÇÃO]
            ${state.candReason}
        """.trimIndent()

        viewModelScope.launch {
            createCandidacyUseCase.execute(
                applicationDate = today,
                statusId = pendenteID, // Inicialmente sem status ou status "Pendente" (se tiver o UUID)
                observations = structuredObservations
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        uiState.value = uiState.value.copy(isSubmittingCandidacy = true, error = null)
                    }
                    is ResultWrapper.Success -> {
                        uiState.value = uiState.value.copy(
                            isSubmittingCandidacy = false,
                            showCandidacyForm = false,
                            // Limpar formulário
                            candFullName = "", candStudentNumber = "",
                            candNif = "", candCourse = "",
                            candContact = "", candReason = ""
                        )
                    }
                    is ResultWrapper.Error -> {
                        println("Erro: ${result.message}")
                        uiState.value = uiState.value.copy(
                            isSubmittingCandidacy = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }
}