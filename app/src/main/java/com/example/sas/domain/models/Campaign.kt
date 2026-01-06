package com.example.sas.domain.models

data class Campaign(
    val id: String,
    val name: String,
    val startDate: String,
    val endDate: String,
    val address: String,
    val type: String,
    val observations: String?,
    val responsibleName: String?,
    val status: CampaignStatus?,
    val createdAt: String?,
    val updatedAt: String?
)