package com.example.sas.domain.models

data class Request(
    val id: String,
    val beneficiary: Beneficiary,
    val requestDate: String, // yyyy/MM/dd
    val description: String?,
    val status: StatusType?,
    val createdAt: String?,
    val updatedAt: String?
)
