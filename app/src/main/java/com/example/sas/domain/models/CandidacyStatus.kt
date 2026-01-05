package com.example.sas.domain.models

data class CandidacyStatus(
    val id: String,
    val code: String,
    val description: String,
    val color: String?,
    val sortOrder: Int? = null
)