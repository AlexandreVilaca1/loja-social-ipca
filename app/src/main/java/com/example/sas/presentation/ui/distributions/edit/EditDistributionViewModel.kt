package com.example.sas.presentation.ui.distributions.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.repositories.DistributionItemsRepository
import com.example.sas.domain.usecase.distributions.CompleteDistributionUseCase
import com.example.sas.domain.usecase.distributions.UpdateDistributionUseCase
import com.example.sas.domain.usecase.logs.LogActivityUseCase
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
    private val completeDistributionUseCase: CompleteDistributionUseCase,
    private val distributionItemsRepository: DistributionItemsRepository,
    private val listUsersUseCase: ListUsersUseCase,
    private val getStatusTypeByCodeUseCase: GetStatusTypeByCodeUseCase,
    private val logActivityUseCase: LogActivityUseCase
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
                statusDescription = distribution.statusDescription,
                statusCode = distribution.statusCode
            )
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            listUsersUseCase.execute(limit = 100, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val sasUsers = result.data?.filter { it.role == "SAS" } ?: emptyList()

                        _uiState.update { it.copy(availableUsers = sasUsers) }
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

        // Validação de permissão - só pode editar se status for POR_ENTREGAR
        if (state.statusCode != "POR_ENTREGAR") {
            _uiState.update {
                it.copy(error = "Apenas distribuições com status 'Por Entregar' podem ser editadas")
            }
            return
        }

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

        // Verificar se o novo status é ENTREGUE
        val newStatusCode = state.availableStatuses.find { it.id == state.selectedStatusId }?.code
        val isCompletingDistribution = newStatusCode == "ENTREGUE"

        viewModelScope.launch {
            // Se o status for ENTREGUE, verificar se há itens na distribuição ANTES de atualizar
            if (isCompletingDistribution) {
                // Verificar se há itens
                val hasItemsCheck = checkDistributionHasItems(state.distributionId)
                if (!hasItemsCheck) {
                    _uiState.update {
                        it.copy(
                            isUpdating = false,
                            error = "Não é possível marcar como 'Entregue'. A distribuição não tem produtos associados. Por favor, adicione produtos antes de marcar como entregue."
                        )
                    }
                    return@launch
                }
            }

            // SEMPRE atualizar a distribuição primeiro com UpdateDistributionUseCase
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
                        // Se completar distribuição (status ENTREGUE), reduzir quantidades dos lotes
                        if (isCompletingDistribution) {
                            // Usar CompleteDistributionUseCase apenas para reduzir quantidades
                            launch {
                                completeDistributionUseCase.execute(
                                    distributionId = state.distributionId,
                                    statusId = state.selectedStatusId
                                ).collect { completeResult ->
                                    when (completeResult) {
                                        is ResultWrapper.Success -> {
                                            logActivityUseCase.execute(
                                                "Completou distribuição:${state.distributionId} (Status: ENTREGUE, quantidades reduzidas)",
                                                "DISTRIBUTION"
                                            )
                                        }
                                        is ResultWrapper.Error -> {
                                            Log.e(TAG, "Failed to reduce lot quantities: ${completeResult.message}")
                                        }
                                        is ResultWrapper.Loading -> {}
                                    }
                                }
                            }
                        } else {
                            logActivityUseCase.execute("Editou distribuição:${state.distributionId}", "DISTRIBUTION")
                        }

                        _uiState.update { it.copy(isUpdating = false) }
                        kotlinx.coroutines.delay(500)
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

    private suspend fun checkDistributionHasItems(distributionId: String): Boolean {
        return try {
            // Coletar resultados, pulando Loading
            var result: ResultWrapper<List<com.example.sas.domain.models.DistributionItem>>? = null
            distributionItemsRepository.listItemsByDistribution(
                distributionId = distributionId,
                limit = 1,
                offset = 0
            ).collect { emittedResult ->
                when (emittedResult) {
                    is ResultWrapper.Loading -> {
                        // Continuar coletando
                    }
                    is ResultWrapper.Success, is ResultWrapper.Error -> {
                        result = emittedResult
                        // Parar de coletar quando tiver resultado final
                    }
                }
            }

            when (result) {
                is ResultWrapper.Success -> {
                    val itemsCount = (result as ResultWrapper.Success).data?.size ?: 0
                    itemsCount > 0
                }
                is ResultWrapper.Error -> {
                    Log.e(TAG, "Error checking distribution items: ${(result as ResultWrapper.Error).message}")
                    false
                }
                else -> {
                    false
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception checking distribution items: ${e.message}", e)
            false
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

