package com.example.sas.domain.models

data class CreateProductRequest(
    val name: String,
    val unitOfMeasure: String,
    val isActive: Boolean = true,
    val description: String? = null,
    val categoryId: String? = null
)