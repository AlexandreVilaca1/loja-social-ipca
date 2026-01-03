package com.example.sas.presentation.ui.beneficiaries

import com.example.sas.domain.models.Beneficiary

/**
 * UI state for the Beneficiarios screen.
 * Represents both form inputs and screen data (list, loading, errors).
 */
data class BeneficiariesUiState(
    // Search
    val searchQuery: String = "",

    // Form inputs
    val fullName: String = "",
    val studentNumber: String = "",
    val nif: String = "",
    val course: String = "",
    val isActive: Boolean = true,
    val contactNumber: String = "",
    val address: String = "",
    val observations: String = "",

    // Screen state
    val isLoading: Boolean = false,
    val error: String? = null,

    // Edit mode
    val isEditMode: Boolean = false,
    val editingBeneficiaryId: String? = null,

    // Results
    val createdBeneficiaryId: String? = null,
    val beneficiaries: List<Beneficiary> = emptyList()
)



