package com.example.sas.domain.models

/**
 * Domain model representing an item in a distribution.
 */
data class DistributionItem(
    val lotId: String,
    val lotCode: String,
    val productName: String,
    val productUnit: String,
    val quantity: Int,
    val observations: String?
)

