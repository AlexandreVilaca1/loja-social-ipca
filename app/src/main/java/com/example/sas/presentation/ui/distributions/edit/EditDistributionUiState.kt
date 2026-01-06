package com.example.sas.presentation.ui.distributions.edit

import com.example.sas.domain.models.StatusType
import com.example.sas.domain.models.User

/**
 * UI State for Edit Distribution screen
 */
data class EditDistributionUiState(
    val distributionId: String = "",
    val distributionDate: String = "",
    val observations: String = "",
    val responsibleStaffName: String = "",
    val statusDescription: String = "",
    val statusCode: String = "",
    val selectedResponsibleStaffId: String = "",
    val selectedStatusId: String = "",
    val availableUsers: List<User> = emptyList(),
    val availableStatuses: List<StatusType> = emptyList(),
    val isUpdating: Boolean = false,
    val error: String? = null
)

