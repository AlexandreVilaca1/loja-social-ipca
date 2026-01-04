package com.example.sas.domain.models

/**
 * Domain model for Status Type.
 */
data class StatusType(
    val id: String,
    val code: String,
    val description: String,
    val color: String? = null
)

