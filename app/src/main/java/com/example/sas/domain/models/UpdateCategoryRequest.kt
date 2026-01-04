package com.example.sas.domain.models

data class UpdateCategoryRequest(
    val id: String,
    val name: String? = null,
    val description: String? = null,
    val active: Boolean? = null
)