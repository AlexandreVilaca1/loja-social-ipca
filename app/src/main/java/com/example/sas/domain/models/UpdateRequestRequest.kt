package com.example.sas.domain.models

data class UpdateRequestRequest(
    val id: String,
    val requestDate: String? = null,
    val description: String? = null,
    val statusId: String? = null
)