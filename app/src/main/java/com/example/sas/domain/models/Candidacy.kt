package com.example.sas.domain.models

data class Candidacy(
    val id: String,
    val applicationDate: String, // Formatada ou como LocalDate
    val status: CandidacyStatus?,
    val observations: String?,
    val createdAt: String,
    val updatedAt: String
)