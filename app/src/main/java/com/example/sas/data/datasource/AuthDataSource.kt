package com.example.sas.data.datasource

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
) {
    /**
     * Authenticates user with email and password.
     */
    suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    /**
     * Gets current Firebase user UID.
     */
    fun getCurrentUserUid(): String? {
        return auth.currentUser?.uid
    }

    /**
     * Gets current Firebase user email.
     */
    fun getCurrentUserEmail(): String? {
        return auth.currentUser?.email
    }

    /**
     * Checks if user is authenticated.
     */
    fun isAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Signs out the current user.
     */
    fun signOut() {
        auth.signOut()
    }
}