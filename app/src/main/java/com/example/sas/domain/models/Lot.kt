package com.example.sas.domain.models

data class Lot(
    val id: String,
    val initialQuantity: Int,
    val currentQuantity: Int,
    val entryDate: String,
    val lotCode: String,
    val expirationDate: String,
    val observations: String?,
    val productId: String?,
    val productName: String?,
    val productUnitOfMeasure: String?,
    val productDescription: String?,
    val createdAt: String?,
    val updatedAt: String?
)
