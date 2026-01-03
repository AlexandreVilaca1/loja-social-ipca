package com.example.sas.presentation.ui.distributionItems

import com.example.sas.domain.models.DistributionItem

/**
 * UI state for Distribution Items screen.
 */
data class DistributionItemsUiState(
    val distributionDate: String = "",
    val items: List<DistributionItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

