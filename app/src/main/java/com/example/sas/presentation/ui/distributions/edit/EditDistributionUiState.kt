package com.example.sas.presentation.ui.distributions.edit

import com.example.sas.domain.models.StatusType
import com.example.sas.domain.models.User

/**
 * UI state for editing a distribution.
 */
data class EditDistributionUiState(
    val distributionId: String = "",
    val distributionDate: String = "",
    val observations: String = "",
    val selectedResponsibleStaffId: String = "",
    val selectedStatusId: String = "",
    val responsibleStaffName: String? = null,
    val statusDescription: String? = null,
    val availableUsers: List<User> = emptyList(),
    val availableStatuses: List<StatusType> = emptyList(),
    val isUpdating: Boolean = false,
    val error: String? = null
)

