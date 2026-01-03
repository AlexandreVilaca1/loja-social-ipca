package com.example.sas.domain.models

data class CreateCategoryRequest(
    val name: String,
    val description: String? = null,
    val active: Boolean = true
)
