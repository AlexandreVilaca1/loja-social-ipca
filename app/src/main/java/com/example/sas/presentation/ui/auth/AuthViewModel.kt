package com.example.sas.presentation.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.User
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import com.example.sas.domain.usecase.auth.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val currentUser: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase
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

    fun refreshUser() {
        loadCurrentUser()
    }
}