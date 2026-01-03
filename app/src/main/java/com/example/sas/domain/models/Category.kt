package com.example.sas.domain.models

data class Category(
    val id: String,
    val name: String,
    val description: String?,
    val active: Boolean,
    val createdAt: String?,
    val updatedAt: String?
)