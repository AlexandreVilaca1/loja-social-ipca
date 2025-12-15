package com.example.sas.presentation.ui.Login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

data class LoginState(
    var email: String? = null,
    var password: String?  = null,
    var error: String? = null,
    var isLoading: Boolean? = null
)
public class LoginViewModel : ViewModel() {

    var uiState = mutableStateOf(LoginState())
        private set

    fun setEmail(email: String) {
        uiState.value = uiState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        uiState.value = uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess : ()-> Unit) {
        uiState.value = uiState.value.copy(isLoading = true)

        if (uiState.value.email.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Email is required",
                isLoading = false
            )
        }

        if (uiState.value.password.isNullOrEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Password is required",
                isLoading = false
            )
        }

        var auth: FirebaseAuth
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(
            uiState.value.email!!,
            uiState.value.password!!
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uiState.value = uiState.value.copy(
                    error = null,
                    isLoading = false
                )
                onLoginSuccess()
            } else {
                uiState.value = uiState.value.copy(
                    error = task.exception?.message,
                    isLoading = false
                )
            }
        }

    }
}
