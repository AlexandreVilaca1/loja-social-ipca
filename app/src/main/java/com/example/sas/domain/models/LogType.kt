package com.example.sas.domain.models

data class LogType(
    val id: String,
    val name: String,
    val code: String?,
    val createdAt: String? = null
)