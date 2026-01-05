package com.example.sas.data.datasource

import android.content.Context
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
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
     * Cria uma nova conta no Firebase Authentication.
     * Retorna o UID do utilizador criado para ser usado na base de dados.
     */
    suspend fun createUserAccount(context: Context, email: String, password: String): String {
        val options = FirebaseApp.getInstance().options

        val secondaryAppName = "SecondaryApp_${System.currentTimeMillis()}"
        val secondaryApp = FirebaseApp.initializeApp(context, options, secondaryAppName)

        try {
            val secondaryAuth = Firebase.auth(secondaryApp)

            val result = secondaryAuth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("Falha ao obter UID")

            secondaryAuth.signOut()

            return uid
        } finally {
            secondaryApp.delete()
        }
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