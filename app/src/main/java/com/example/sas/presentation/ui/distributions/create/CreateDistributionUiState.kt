package com.example.sas.presentation.ui.distributions.create

import com.example.sas.domain.models.User

/**
 * UI state for Create Distribution screen.
 */
data class CreateDistributionUiState(
    val beneficiaryId: String = "",
    val distributionDate: String = "",
    val selectedResponsibleStaffId: String = "",
    val selectedStatusId: String = "",
    val observations: String = "",
    val availableUsers: List<User> = emptyList(),
    val isLoadingUsers: Boolean = false,
    val isCreating: Boolean = false,
    val error: String? = null
)

