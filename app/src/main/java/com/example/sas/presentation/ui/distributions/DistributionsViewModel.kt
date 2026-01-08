package com.example.sas.presentation.ui.distributions

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.repositories.DistributionItemsRepository
import com.example.sas.domain.usecase.beneficiaries.GetBeneficiaryByIdUseCase
import com.example.sas.domain.usecase.distributions.*
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
class DistributionsViewModel @Inject constructor(
    private val listDistributionsUseCase: ListDistributionsByBeneficiaryUseCase,
    private val listDistributionsByStatusUseCase: ListDistributionsByBeneficiaryAndStatusUseCase,
    private val createDistributionUseCase: CreateDistributionUseCase,
    private val updateDistributionUseCase: UpdateDistributionUseCase,
    private val listUsersUseCase: ListUsersUseCase,
    private val getStatusByCodeUseCase: GetStatusTypeByCodeUseCase,
    private val getStatusTypeByCodeUseCase: GetStatusTypeByCodeUseCase,
    private val logActivityUseCase: LogActivityUseCase,
    private val getBeneficiaryByIdUseCase: GetBeneficiaryByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DistributionsUiState())
    val uiState: StateFlow<DistributionsUiState> = _uiState.asStateFlow()

    private val beneficiaryIdFromNav: String? = savedStateHandle["beneficiaryId"]
    private val beneficiaryNameFromNav: String? = savedStateHandle["beneficiaryName"]

    val currentBeneficiaryId: String? get() = beneficiaryIdFromNav

    private companion object {
        const val TAG = "DistributionsVM"
    }

    init {
        beneficiaryNameFromNav?.let { name ->
            _uiState.update { it.copy(beneficiaryName = name) }
        }
        loadDistributions()
        loadUsers()
        loadDefaultStatus()
        loadStatusTypes()
    }

    // --- LÓGICA DE LISTAGEM ---

    private fun loadDistributions() {
        val id = beneficiaryIdFromNav ?: return
        val statusFilter = _uiState.value.selectedStatusFilter

        viewModelScope.launch {
            val useCase = if (statusFilter != null) {
                listDistributionsByStatusUseCase.execute(id, statusFilter, 100, 0)
            } else {
                listDistributionsUseCase.execute(id, 100, 0)
            }

            useCase.collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> _uiState.update { it.copy(isLoading = true, error = null) }
                    is ResultWrapper.Success -> _uiState.update { it.copy(isLoading = false, distributions = result.data ?: emptyList()) }
                    is ResultWrapper.Error -> _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
            }
        }
    }

    fun filterByStatus(statusCode: String?) {
        _uiState.update { it.copy(selectedStatusFilter = statusCode) }
        loadDistributions()
    }

    fun refreshDistributions() {
        loadDistributions()
    }

    // --- LÓGICA DE SUPORTE (USERS E STATUS) ---

    private fun loadUsers() {
        viewModelScope.launch {
            listUsersUseCase.execute(limit = 100, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> _uiState.update { it.copy(isLoadingUsers = true) }
                    is ResultWrapper.Success -> {
                        val sasUsers = result.data?.filter { it.role == "SAS" } ?: emptyList()
                        _uiState.update { it.copy(isLoadingUsers = false, availableUsers = sasUsers) }
                    }
                    is ResultWrapper.Error -> _uiState.update { it.copy(isLoadingUsers = false, error = result.message) }
                }
            }
        }
    }

    private fun loadDefaultStatus() {
        viewModelScope.launch {
            getStatusByCodeUseCase.execute("POR_ENTREGAR").collect { result ->
                if (result is ResultWrapper.Success) {
                    _uiState.update { it.copy(selectedStatusId = result.data?.id ?: "") }
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
                    if (result is ResultWrapper.Success && result.data != null) statusTypes.add(result.data)
                }
            }
            _uiState.update { it.copy(availableStatuses = statusTypes) }
        }
    }

    // --- LÓGICA DE FORMULÁRIO (COMMONS) ---

    fun onBeneficiaryIdChanged(id: String) = _uiState.update { it.copy(beneficiaryId = id) }

    fun onDistributionDateChanged(date: String) = _uiState.update { it.copy(distributionDate = date) }

    fun onResponsibleStaffSelected(userId: String) = _uiState.update { it.copy(selectedResponsibleStaffId = userId) }

    fun onStatusSelected(statusId: String) = _uiState.update { it.copy(selectedStatusId = statusId) }

    fun onObservationsChanged(observations: String) = _uiState.update { it.copy(observations = observations) }

    fun clearError() = _uiState.update { it.copy(error = null) }

    fun cancelEdit() {
        _uiState.update {
            it.copy(
                distributionDate = "",
                observations = "",
                selectedResponsibleStaffId = "",
                error = null
            )
        }
    }


    fun createDistribution(onSuccess: (String) -> Unit) {
        val state = _uiState.value
        var benName = ""

        if (state.beneficiaryId.isBlank() || state.distributionDate.isBlank() ||
            state.selectedResponsibleStaffId.isBlank() || state.selectedStatusId.isBlank()) {
            _uiState.update { it.copy(error = "Preencha os campos obrigatórios") }
            return
        }

        // Validar que a data não seja anterior à data atual
        if (!isDateValidForDistribution(state.distributionDate)) {
            _uiState.update { it.copy(error = "A data da distribuição não pode ser anterior à data atual") }
            return
        }

        viewModelScope.launch {
            getBeneficiaryByIdUseCase.execute(state.beneficiaryId).collect { result ->
                if(result is ResultWrapper.Success) benName = result.data?.fullName ?: "Nome Desconhecido"
            }

            createDistributionUseCase.execute(
                beneficiaryId = state.beneficiaryId,
                distributionDate = state.distributionDate,
                responsibleStaffId = state.selectedResponsibleStaffId,
                statusId = state.selectedStatusId,
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> _uiState.update { it.copy(isCreating = true, error = null) }
                    is ResultWrapper.Success -> {
                        logActivityUseCase.execute("Criou distribuição: ${benName} ${state.distributionDate}", "DISTRIBUTION")
                        _uiState.update { it.copy(isCreating = false) }
                        result.data?.let { onSuccess(it) }
                    }
                    is ResultWrapper.Error -> _uiState.update { it.copy(isCreating = false, error = result.message) }
                }
            }
        }
    }

    // --- LÓGICA DE EDIÇÃO ---

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

    fun updateDistribution(onSuccess: () -> Unit) {
        val state = _uiState.value
        var benName = ""

        if (state.distributionDate.isBlank() || state.selectedResponsibleStaffId.isBlank() || state.selectedStatusId.isBlank()) {
            _uiState.update { it.copy(error = "Campos obrigatórios em falta") }
            return
        }

        val formattedDate = state.distributionDate.replace("/", "-")

        viewModelScope.launch {
            if (isCompleting && !checkDistributionHasItems(state.distributionId)) {
                _uiState.update { it.copy(error = "Adicione produtos antes de marcar como entregue.") }
                return@launch
            }

            getBeneficiaryByIdUseCase.execute(state.beneficiaryId).collect { result ->
                if(result is ResultWrapper.Success) benName = result.data?.fullName ?: "Nome Desconhecido"
            }

            updateDistributionUseCase.execute(
                distributionId = state.distributionId,
                distributionDate = formattedDate,
                responsibleStaffId = state.selectedResponsibleStaffId,
                statusId = state.selectedStatusId,
                observations = state.observations.ifBlank { null }
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> _uiState.update { it.copy(isUpdating = true, error = null) }
                    is ResultWrapper.Success -> {
                        if (isCompleting) {
                            launch {
                                completeDistributionUseCase.execute(state.distributionId, state.selectedStatusId).collect { completeResult ->
                                    if (completeResult is ResultWrapper.Success) {
                                        logActivityUseCase.execute("Completou distribuição:${benName} ${formattedDate}", "DISTRIBUTION")
                                    }
                                }
                            }
                        } else {
                            logActivityUseCase.execute("Editou distribuição:${benName} ${formattedDate}", "DISTRIBUTION")
                        }
                        _uiState.update { it.copy(isUpdating = false) }
                        kotlinx.coroutines.delay(500)
                        onSuccess()
                    }
                    is ResultWrapper.Error -> _uiState.update { it.copy(isUpdating = false, error = result.message) }
                }
            }
        }
    }


    /**
     * Valida se a data é válida para distribuição (não pode ser anterior à data atual)
     * @param dateString Data no formato "yyyy-MM-dd" ou "yyyy/MM/dd"
     * @return true se a data for hoje ou futura, false se for passada
     */
    private fun isDateValidForDistribution(dateString: String): Boolean {
        return try {
            val sdf = java.text.SimpleDateFormat(
                if (dateString.contains("/")) "yyyy/MM/dd" else "yyyy-MM-dd",
                java.util.Locale.getDefault()
            )
            sdf.isLenient = false
            val distributionDate = sdf.parse(dateString) ?: return false

            val calendar = java.util.Calendar.getInstance()
            calendar.set(java.util.Calendar.HOUR_OF_DAY, 0)
            calendar.set(java.util.Calendar.MINUTE, 0)
            calendar.set(java.util.Calendar.SECOND, 0)
            calendar.set(java.util.Calendar.MILLISECOND, 0)
            val today = calendar.time

            !distributionDate.before(today)
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao validar data: ${e.message}")
            false
        }
    }
}