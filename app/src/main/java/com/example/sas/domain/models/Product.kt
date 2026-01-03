package com.example.sas.domain.models

data class Product(
    val id: String,
    val name: String,
    val unitOfMeasure: String,
    val isActive: Boolean,
    val description: String?,
    val categoryId: String?,
    val categoryName: String?,
    val categoryDescription: String?,
    val createdAt: String?,
    val updatedAt: String?
)