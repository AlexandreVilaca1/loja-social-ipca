package com.example.sas.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.usecase.auth.CreateAuthAccountUseCase
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sas.domain.usecase.auth.SignOutUseCase
import com.example.sas.domain.usecase.users.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val currentUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,

    val showCreateUserDialog: Boolean = false,
    val isCreatingUser: Boolean = false,
    val createUserError: String? = null,
    val newUserForm: NewUserForm = NewUserForm()
)

data class NewUserForm(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "USER"
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val createAuthAccountUseCase: CreateAuthAccountUseCase,
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    init {
        loadCurrentUser()
    }

    fun loadCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase.execute().collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                    }
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            currentUser = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            }
        }
    }

    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            signOutUseCase.execute().collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(currentUser = null)
                        onSuccess()
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message)
                    }
                    else -> { }
                }
            }
        }
    }

    fun toggleCreateUserDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(
            showCreateUserDialog = show,
            createUserError = null,
            newUserForm = if (!show) NewUserForm() else _uiState.value.newUserForm
        )
    }

    fun onNewUserFormChange(
        name: String? = null,
        email: String? = null,
        password: String? = null,
        role: String? = null
    ) {
        _uiState.value = _uiState.value.copy(
            newUserForm = _uiState.value.newUserForm.copy(
                name = name ?: _uiState.value.newUserForm.name,
                email = email ?: _uiState.value.newUserForm.email,
                password = password ?: _uiState.value.newUserForm.password,
                role = role ?: _uiState.value.newUserForm.role
            )
        )
    }

    fun createNewUser(context: android.content.Context) {
        val form = _uiState.value.newUserForm

        // Validação simples
        if (form.email.isBlank() || form.password.isBlank() || form.name.isBlank()) {
            _uiState.value = _uiState.value.copy(createUserError = "Preencha todos os campos obrigatórios")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreatingUser = true, createUserError = null)

            createAuthAccountUseCase.execute(context, form.email, form.password).collect { authResult ->
                when (authResult) {
                    is ResultWrapper.Success -> {
                        val newUid = authResult.data ?: ""

                        createUserUseCase.execute(
                            uid = newUid,
                            name = form.name,
                            email = form.email,
                            passwordHash = "",
                            role = form.role
                        ).collect { dbResult ->
                            when (dbResult) {
                                is ResultWrapper.Success -> {
                                    _uiState.value = _uiState.value.copy(
                                        isCreatingUser = false,
                                        showCreateUserDialog = false,
                                        newUserForm = NewUserForm()
                                    )
                                }
                                is ResultWrapper.Error -> {
                                    _uiState.value = _uiState.value.copy(
                                        isCreatingUser = false,
                                        createUserError = dbResult.message
                                    )
                                }
                                else -> {}
                            }
                        }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isCreatingUser = false,
                            createUserError = authResult.message
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun refreshUser() {
        loadCurrentUser()
    }
}