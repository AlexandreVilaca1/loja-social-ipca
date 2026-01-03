package com.example.sas.domain.models

data class UpdateLotRequest(
    val id: String,
    val initialQuantity: Int? = null,
    val currentQuantity: Int? = null,
    val entryDate: String? = null,
    val lotCode: String? = null,
    val expirationDate: String? = null,
    val observations: String? = null,
    val productId: String? = null
)
