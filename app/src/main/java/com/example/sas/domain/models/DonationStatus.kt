package com.example.sas.domain.models

data class DonationStatus(
    val id: String,
    val code: String,
    val description: String,
    val color: String? = null
)