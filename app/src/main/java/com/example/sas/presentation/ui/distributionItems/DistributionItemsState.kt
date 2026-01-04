package com.example.sas.presentation.ui.distributionItems

import com.example.sas.domain.models.DistributionItem
import com.example.sas.domain.models.Lot

/**
 * UI state for Distribution Items screen.
 */
data class DistributionItemsUiState(
    val distributionDate: String = "",
    val distributionStatusCode: String? = null,
    val items: List<DistributionItem> = emptyList(),
    val availableLots: List<Lot> = emptyList(),
    val isLoading: Boolean = false,
    val isCreating: Boolean = false,
    val error: String? = null
)

