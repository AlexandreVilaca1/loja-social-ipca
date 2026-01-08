package com.example.sas.presentation.ui.campaigns.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Campaign
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.usecase.auth.GetLoggedUserRoleUseCase
import com.example.sas.domain.usecase.campaigns.*
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import com.example.sas.domain.usecase.status.ListStatusTypesUseCase
import com.google.firebase.dataconnect.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class CampaignsUiState(
    val campaigns: List<Campaign> = emptyList(),
    val statusTypes: List<StatusType> = emptyList(), // Substituído aqui
    val isLoading: Boolean = false,
    val error: String? = null,
    val userRole: String? = null,
    val searchQuery: String = "",
    val selectedType: String? = null,
    val availableTypes: List<String> = listOf("Interna", "Externa", "Bens Essenciais", "Educativa"),

    // Form fields
    val name: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val address: String = "",
    val campaignType: String = "Alimentar",
    val observations: String = "",
    val statusId: String? = null,
    val isEditMode: Boolean = false,
    val editingCampaignId: String? = null
)

@HiltViewModel
class CampaignsViewModel @Inject constructor(
    private val listCampaignsUseCase: ListCampaignsUseCase,
    private val searchCampaignsUseCase: SearchCampaignsUseCase,
    private val createCampaignUseCase: CreateCampaignUseCase,
    private val updateCampaignUseCase: UpdateCampaignUseCase,
    private val deleteCampaignUseCase: DeleteCampaignUseCase,
    private val listStatusTypesUseCase: ListStatusTypesUseCase,
    private val getLoggedUserRoleUseCase: GetLoggedUserRoleUseCase,
    private val logActivityUseCase: LogActivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CampaignsUiState())
    val uiState: StateFlow<CampaignsUiState> = _uiState.asStateFlow()

    init {
        loadCampaigns()
        loadStatusTypes()
        fetchUserRole()
    }

    private fun fetchUserRole() {
        viewModelScope.launch {
            getLoggedUserRoleUseCase.execute().collect { result ->
                if (result is ResultWrapper.Success) _uiState.update { it.copy(userRole = result.data) }
            }
        }
    }

    fun loadStatusTypes() {
        viewModelScope.launch {
            listStatusTypesUseCase.execute().collect { result ->
                if (result is ResultWrapper.Success) {
                    _uiState.update { it.copy(statusTypes = result.data ?: emptyList()) }
                }
            }
        }
    }

    fun loadCampaigns() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            listCampaignsUseCase.execute(null, null).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> _uiState.update { it.copy(campaigns = result.data ?: emptyList(), isLoading = false) }
                    is ResultWrapper.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
                    else -> {}
                }
            }
        }
    }

    // --- Lógica de Status Automático ---
    private fun determineStatusCode(start: LocalDate, end: LocalDate): String {
        val today = Calendar.getInstance()
        val todayDate = LocalDate(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH))

        return when {
            isBefore(todayDate, start) -> "POR_INICIAR"
            isAfter(todayDate, end) -> "FINALIZADA"
            else -> "A_DECORRER"
        }
    }

    private fun isBefore(a: LocalDate, b: LocalDate): Boolean {
        if (a.year < b.year) return true
        if (a.year == b.year && a.month < b.month) return true
        return a.year == b.year && a.month == b.month && a.day < b.day
    }

    private fun isAfter(a: LocalDate, b: LocalDate): Boolean {
        if (a.year > b.year) return true
        if (a.year == b.year && a.month > b.month) return true
        return a.year == b.year && a.month == b.month && a.day > b.day
    }

    // --- Eventos do Formulário ---
    fun onEvent(event: CampaignFormEvent) {
        when(event) {
            is CampaignFormEvent.NameChanged -> _uiState.update { it.copy(name = event.value) }
            is CampaignFormEvent.AddressChanged -> _uiState.update { it.copy(address = event.value) }
            is CampaignFormEvent.DatesChanged -> _uiState.update { it.copy(startDate = event.start, endDate = event.end) }
            is CampaignFormEvent.TypeChanged -> _uiState.update { it.copy(campaignType = event.value) }
            is CampaignFormEvent.ObsChanged -> _uiState.update { it.copy(observations = event.value) }
            is CampaignFormEvent.StatusChanged -> _uiState.update { it.copy(statusId = event.value) }
            is CampaignFormEvent.Submit -> if (_uiState.value.isEditMode) updateCampaign() else createCampaign()
        }
    }

    private fun createCampaign() {
        val state = _uiState.value
        viewModelScope.launch {
            val start = parseDate(state.startDate) ?: return@launch
            val end = parseDate(state.endDate) ?: return@launch

            val statusCode = determineStatusCode(start, end)
            val statusId = state.statusTypes.find { it.code == statusCode }?.id

            createCampaignUseCase.execute(
                state.name,
                start,
                end,
                state.address,
                state.campaignType,
                state.observations.ifBlank { null },
                null, statusId
            ).collect {
                if (it is ResultWrapper.Success) {
                    logActivityUseCase.execute("Criou Campanha: ${state.name}", "CAMPAIGN")
                    loadCampaigns(); clearForm()
                }
            }
        }
    }

    private fun updateCampaign() {
        val state = _uiState.value
        viewModelScope.launch {
            updateCampaignUseCase.execute(
                state.editingCampaignId!!,
                state.name,
                parseDate(state.startDate),
                parseDate(state.endDate),
                state.address, state.campaignType,
                state.observations,
                null,
                state.statusId
            ).collect {
                if (it is ResultWrapper.Success) {
                    logActivityUseCase.execute("Editou Campanha: ${state.name}", "CAMPAIGN")
                    loadCampaigns(); clearForm()
                }
            }
        }
    }

    fun deleteCampaign(id: String, name: String) {
        viewModelScope.launch {
            deleteCampaignUseCase.execute(id).collect {
                if (it is ResultWrapper.Success<*>){
                    logActivityUseCase.execute("Removeu Campanha: ${name}", "CAMPAIGN")
                    loadCampaigns()
                }
            }
        }
    }

    fun startEdit(campaign: Campaign) {
        _uiState.update { it.copy(
            isEditMode = true,
            editingCampaignId = campaign.id,
            name = campaign.name,
            address = campaign.address,
            campaignType = campaign.type,
            observations = campaign.observations ?: "",
            startDate = reverseFormat(campaign.startDate),
            endDate = reverseFormat(campaign.endDate),
            statusId = campaign.status?.id
        )}
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        viewModelScope.launch {
            searchCampaignsUseCase.execute(query.ifBlank { null }).collect { result ->
                if (result is ResultWrapper.Success) _uiState.update { it.copy(campaigns = result.data ?: emptyList()) }
            }
        }
    }

    fun setTypeFilter(type: String?) = _uiState.update { it.copy(selectedType = type) }

    fun clearForm() = _uiState.update {
        it.copy(
            name = "", startDate = "", endDate = "", address = "", observations = "",
            statusId = null, isEditMode = false, editingCampaignId = null
        )
    }

    private fun parseDate(date: String): LocalDate? = try { val p = date.split("-"); LocalDate(p[0].toInt(), p[1].toInt(), p[2].toInt()) } catch (e: Exception) { null }
    private fun reverseFormat(date: String): String = try { val p = date.split("/"); "${p[2]}-${p[1]}-${p[0]}" } catch (e: Exception) { "" }
}

sealed class CampaignFormEvent {
    data class NameChanged(val value: String): CampaignFormEvent()
    data class AddressChanged(val value: String): CampaignFormEvent()
    data class DatesChanged(val start: String, val end: String): CampaignFormEvent()
    data class TypeChanged(val value: String): CampaignFormEvent()
    data class ObsChanged(val value: String): CampaignFormEvent()
    data class StatusChanged(val value: String): CampaignFormEvent()
    object Submit: CampaignFormEvent()
}