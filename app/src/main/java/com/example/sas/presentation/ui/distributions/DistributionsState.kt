package com.example.sas.presentation.ui.distributions

import com.example.sas.domain.models.Distribution

/**
 * UI state for Beneficiary Distributions screen.
 */
data class DistributionsUiState(
    val beneficiaryName: String = "",
    val distributions: List<Distribution> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

