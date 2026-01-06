package com.example.sas.domain.models

data class DonationItem(
    val donationId: String,
    val productId: String,
    val quantity: Int,
    val categoryName: String?,
    val state: StatusType?,
    val observations: String?,
    val createdAt: String?
)