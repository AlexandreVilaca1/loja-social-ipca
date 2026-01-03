package com.example.sas.domain.models

/**
 * Domain model representing a beneficiary.
 * This is independent of any data source implementation.
 */
data class Beneficiary(
    val id: String,
    val fullName: String,
    val studentNumber: Int,
    val nif: String,
    val course: String,
    val isActive: Boolean,
    val contactNumber: String,
    val address: String? = null,
    val observations: String? = null,
    val createdAt: String? = null
)
