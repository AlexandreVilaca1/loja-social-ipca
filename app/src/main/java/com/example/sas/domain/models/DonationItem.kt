package com.example.sas.domain.models

data class DonationItem(
    val donationId: String,
    val donorName: String,
    val productId: String,
    val productName: String,
    val quantity: Int,
    val unitOfMeasure: String,
    val categoryName: String?,
    val state: DonationStatus?,
    val observations: String?,
    val createdAt: String?
)