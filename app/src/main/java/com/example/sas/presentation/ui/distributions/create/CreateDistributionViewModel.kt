package com.example.sas.presentation.ui.distributions.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.distributions.CreateDistributionUseCase
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
class CreateDistributionViewModel @Inject constructor(
    private val createDistributionUseCase: CreateDistributionUseCase,
    private val listUsersUseCase: ListUsersUseCase,
    private val getStatusByCodeUseCase: GetStatusTypeByCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateDistributionUiState())
    val uiState: StateFlow<CreateDistributionUiState> = _uiState.asStateFlow()

    private companion object {
        const val TAG = "CreateDistributionVM"
    }

    init {
        loadUsers()
        loadDefaultStatus()
    }

    private fun loadDefaultStatus() {
        viewModelScope.launch {
            Log.d(TAG, "Loading default status NAO_ENTREGUE...")

            getStatusByCodeUseCase.execute("NAO_ENTREGUE")
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            // Status loading
                        }
                        is ResultWrapper.Success -> {
                            val statusId = result.data?.id
                            if (statusId != null) {
                                Log.d(TAG, "Found status NAO_ENTREGUE with ID: $statusId")
                                _uiState.update { it.copy(selectedStatusId = statusId) }
                            } else {
                                Log.e(TAG, "Status NAO_ENTREGUE not found")
                                _uiState.update {
                                    it.copy(error = "Status NAO_ENTREGUE não encontrado no sistema")
                                }
                            }
                        }
                        is ResultWrapper.Error -> {
                            Log.e(TAG, "Failed to load status: ${result.message}")
                            _uiState.update {
                                it.copy(error = result.message ?: "Erro ao carregar status")
                            }
                        }
                    }
                }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            Log.d(TAG, "Loading users...")

            listUsersUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            _uiState.update { it.copy(isLoadingUsers = true) }
                        }
                        is ResultWrapper.Success -> {
                            Log.d(TAG, "Loaded ${result.data?.size ?: 0} users")
                            _uiState.update {
                                it.copy(
                                    isLoadingUsers = false,
                                    availableUsers = result.data ?: emptyList()
                                )
                            }
                        }
                        is ResultWrapper.Error -> {
                            Log.e(TAG, "Failed to load users: ${result.message}")
                            _uiState.update {
                                it.copy(
                                    isLoadingUsers = false,
                                    error = result.message ?: "Erro ao carregar utilizadores"
                                )
                            }
                        }
                    }
                }
        }
    }

    fun onBeneficiaryIdChanged(id: String) {
        _uiState.update { it.copy(beneficiaryId = id) }
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

    fun createDistribution(onSuccess: (String) -> Unit) {
        val state = _uiState.value

        // Validações
        if (state.beneficiaryId.isBlank()) {
            _uiState.update { it.copy(error = "Beneficiário é obrigatório") }
            return
        }
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

        viewModelScope.launch {
            Log.d(TAG, "Creating distribution...")

            createDistributionUseCase.execute(
                beneficiaryId = state.beneficiaryId,
                distributionDate = state.distributionDate,
                responsibleStaffId = state.selectedResponsibleStaffId,
                statusId = state.selectedStatusId,
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isCreating = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "Distribution created successfully: ${result.data}")
                        _uiState.update { it.copy(isCreating = false) }
                        result.data?.let { onSuccess(it) }
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Failed to create distribution: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isCreating = false,
                                error = result.message ?: "Erro ao criar distribuição"
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

