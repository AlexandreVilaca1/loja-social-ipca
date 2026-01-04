package com.example.sas.presentation.ui.distributions.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.usecase.distributions.UpdateDistributionUseCase
import com.example.sas.domain.usecase.status.GetStatusTypeByCodeUseCase
import com.example.sas.domain.usecase.users.ListUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDistributionViewModel @Inject constructor(
    private val updateDistributionUseCase: UpdateDistributionUseCase,
    private val listUsersUseCase: ListUsersUseCase,
    private val getStatusTypeByCodeUseCase: GetStatusTypeByCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditDistributionUiState())
    val uiState: StateFlow<EditDistributionUiState> = _uiState.asStateFlow()

    private companion object {
        const val TAG = "EditDistributionVM"
    }

    init {
        loadUsers()
        loadStatusTypes()
    }

    fun initializeWithDistribution(distribution: Distribution) {
        _uiState.update {
            it.copy(
                distributionId = distribution.id,
                distributionDate = distribution.distributionDate,
                observations = distribution.observations ?: "",
                responsibleStaffName = distribution.responsibleStaffName,
                statusDescription = distribution.statusDescription
            )
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            listUsersUseCase.execute(limit = 100, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.update { it.copy(availableUsers = result.data ?: emptyList()) }
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Failed to load users: ${result.message}")
                    }
                    is ResultWrapper.Loading -> {}
                }
            }
        }
    }

    private fun loadStatusTypes() {
        viewModelScope.launch {
            val statusCodes = listOf("POR_ENTREGAR", "ENTREGUE", "NAO_COMPARECEU")
            val statusTypes = mutableListOf<StatusType>()

            statusCodes.forEach { code ->
                getStatusTypeByCodeUseCase.execute(code).collect { result ->
                    if (result is ResultWrapper.Success && result.data != null) {
                        statusTypes.add(result.data)
                    }
                }
            }

            _uiState.update { it.copy(availableStatuses = statusTypes) }
        }
    }

    fun onDistributionDateChanged(date: String) {
        _uiState.update { it.copy(distributionDate = date) }
    }

    fun onResponsibleStaffSelected(userId: String) {
        _uiState.update { it.copy(selectedResponsibleStaffId = userId) }
    }

    fun onStatusSelected(statusId: String) {
        _uiState.update { it.copy(selectedStatusId = statusId) }
    }

    fun onObservationsChanged(observations: String) {
        _uiState.update { it.copy(observations = observations) }
    }

    fun updateDistribution(onSuccess: () -> Unit) {
        val state = _uiState.value

        // Validações
        if (state.distributionDate.isBlank()) {
            _uiState.update { it.copy(error = "Data da distribuição é obrigatória") }
            return
        }
        if (state.selectedResponsibleStaffId.isBlank()) {
            _uiState.update { it.copy(error = "Funcionário responsável é obrigatório") }
            return
        }
        if (state.selectedStatusId.isBlank()) {
            _uiState.update { it.copy(error = "Status é obrigatório") }
            return
        }

        // Converter data de YYYY/MM/DD para YYYY-MM-DD
        val formattedDate = state.distributionDate.replace("/", "-")

        viewModelScope.launch {
            Log.d(TAG, "Updating distribution: ${state.distributionId}")

            updateDistributionUseCase.execute(
                distributionId = state.distributionId,
                distributionDate = formattedDate,
                responsibleStaffId = state.selectedResponsibleStaffId,
                statusId = state.selectedStatusId,
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isUpdating = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "Distribution updated successfully")
                        _uiState.update { it.copy(isUpdating = false) }
                        onSuccess()
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Failed to update distribution: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isUpdating = false,
                                error = result.message ?: "Erro ao atualizar distribuição"
                            )
                        }
                    }
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

