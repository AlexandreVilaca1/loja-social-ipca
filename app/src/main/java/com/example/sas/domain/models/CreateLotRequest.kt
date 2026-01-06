package com.example.sas.domain.models

data class CreateLotRequest(
    val initialQuantity: Int,
    val currentQuantity: Int,
    val entryDate: String, // Format: "yyyy-MM-dd"
    val lotCode: String,
    val expirationDate: String? = null, // Format: "yyyy-MM-dd" - Optional
    val observations: String? = null,
    val productId: String? = null
)
