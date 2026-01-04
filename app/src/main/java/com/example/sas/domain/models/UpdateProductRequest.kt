package com.example.sas.domain.models

data class UpdateProductRequest(
    val id: String,
    val name: String? = null,
    val unitOfMeasure: String? = null,
    val isActive: Boolean? = null,
    val description: String? = null,
    val categoryId: String? = null
)