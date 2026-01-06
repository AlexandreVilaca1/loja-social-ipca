package com.example.sas.domain.models

data class Donation(
    val id: String,
    val donationDate: String,
    val donorName: String,
    val estimatedValue: Double,
    val donorContact: String?,
    val observations: String?,
    val campaignName: String?,
    val status: DonationStatus?,
    val createdAt: String?,
    val updatedAt: String?
)