package com.example.sas.domain.models

/**
 * Domain model for User.
 */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val role: String? = null
)

