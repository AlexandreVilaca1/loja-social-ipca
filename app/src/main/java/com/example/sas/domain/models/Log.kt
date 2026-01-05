package com.example.sas.domain.models

data class Log(
    val id: String,
    val description: String,
    val userId: String,
    val userName: String,
    val logTypeCode: String,
    val logTypeName: String,
    val createdAt: String
)
