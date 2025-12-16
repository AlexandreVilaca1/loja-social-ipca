package com.example.sas.data.datasource

import com.example.sas.domain.common.ResultWrapper
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val auth: FirebaseAuth
){
    suspend fun authenticate(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password).await()
    }
}