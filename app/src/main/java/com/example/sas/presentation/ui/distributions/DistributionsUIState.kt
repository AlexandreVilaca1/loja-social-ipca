package com.example.sas.presentation.ui.distributions

import com.example.sas.domain.models.Distribution
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.models.User

data class DistributionsUiState(
    // Listagem
    val distributions: List<Distribution> = emptyList(),
    val beneficiaryName: String = "",
    val isLoading: Boolean = false,
    val selectedStatusFilter: String? = null,

    // Formário (Criação e Edição)
    val distributionId: String = "",
    val beneficiaryId: String = "",
    val distributionDate: String = "",
    val observations: String = "",
    val selectedResponsibleStaffId: String = "",
    val selectedStatusId: String = "",

    // Dados de suporte
    val availableUsers: List<User> = emptyList(),
    val availableStatuses: List<StatusType> = emptyList(),
    val isLoadingUsers: Boolean = false,
    val isCreating: Boolean = false,
    val isUpdating: Boolean = false,
    val error: String? = null,

    // Campos informativos para Edição
    val responsibleStaffName: String? = null,
    val statusDescription: String? = null,
    val statusCode: String? = null
)