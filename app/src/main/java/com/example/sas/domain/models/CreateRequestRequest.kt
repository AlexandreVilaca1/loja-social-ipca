package com.example.sas.domain.models

data class CreateRequestRequest(
    val beneficiaryId: String,
    val requestDate: String,
    val description: String?,
    val statusId: String?
)