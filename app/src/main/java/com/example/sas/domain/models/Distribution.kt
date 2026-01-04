package com.example.sas.domain.models

/**
 * Domain model representing a distribution to a beneficiary.
 */
data class Distribution(
    val id: String,
    val distributionDate: String,
    val observations: String?,
    val responsibleStaffName: String?,
    val beneficiaryName: String?,
    val statusCode: String?,
    val statusDescription: String?,
    val createdAt: String?
)

