package com.example.sas.presentation.ui.donations.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Donation
import com.example.sas.domain.models.Campaign
import com.example.sas.domain.usecase.campaigns.ListCampaignsUseCase
import com.example.sas.domain.usecase.donations.*
import com.example.sas.domain.usecase.auth.GetLoggedUserRoleUseCase
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import com.google.firebase.dataconnect.LocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

data class DonationsUiState(
    val donations: List<Donation> = emptyList(),
    val campaigns: List<Campaign> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userRole: String? = null,

    // Filtros
    val searchQuery: String = "",
    val filterCampaignId: String? = null,
    val filterDate: String = "", // yyyy-MM-dd

    // Campos do Formulário
    val donorName: String = "",
    val donationDate: String = "", // yyyy-MM-dd
    val estimatedValue: String = "",
    val donorContact: String = "",
    val observations: String = "",
    val selectedCampaignId: String? = null,

    val isEditMode: Boolean = false,
    val editingDonationId: String? = null
)

@HiltViewModel
class DonationsViewModel @Inject constructor(
    private val listDonationsUseCase: ListDonationsUseCase,
    private val listDonationsByCampaignUseCase: ListDonationsByCampaignUseCase,
    private val createDonationUseCase: CreateDonationUseCase,
    private val updateDonationUseCase: UpdateDonationUseCase,
    private val deleteDonationUseCase: DeleteDonationUseCase,
    private val listCampaignsUseCase: ListCampaignsUseCase,
    private val getLoggedUserRoleUseCase: GetLoggedUserRoleUseCase,
    private val logActivityUseCase: LogActivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DonationsUiState())
    val uiState: StateFlow<DonationsUiState> = _uiState.asStateFlow()

    init {
        fetchUserRole()
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch {
                listCampaignsUseCase.execute(null, null).collect { res ->
                    if (res is ResultWrapper.Success) _uiState.update { it.copy(campaigns = res.data ?: emptyList()) }
                }
            }
            loadDonations()
        }
    }

    // Carrega a base de dados (Todas ou por Campanha)
    fun loadDonations() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val campaignId = _uiState.value.filterCampaignId
            val flow = if (campaignId != null) {
                listDonationsByCampaignUseCase.execute(campaignId, 200, 0)
            } else {
                listDonationsUseCase.execute(limit = 200, offset = 0)
            }

            flow.collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.update { it.copy(donations = result.data ?: emptyList(), isLoading = false) }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun setSearchQuery(query: String) {
        // Apenas atualiza o estado. A filtragem será feita na View.
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun setFilterCampaign(id: String?) {
        _uiState.update { it.copy(filterCampaignId = id) }
        loadDonations() // Recarrega do servidor ao mudar de campanha
    }

    fun setFilterDate(date: String) {
        _uiState.update { it.copy(filterDate = date) }
    }

    // ... (restantes métodos create/update/delete/form iguais)
    fun clearForm() { _uiState.update { it.copy(donorName = "", donationDate = "", estimatedValue = "", donorContact = "", observations = "", selectedCampaignId = null, isEditMode = false, editingDonationId = null) } }
    fun updateFormField(field: String, value: String) {
        when(field) {
            "name" -> _uiState.update { it.copy(donorName = value) }
            "value" -> _uiState.update { it.copy(estimatedValue = value) }
            "contact" -> _uiState.update { it.copy(donorContact = value) }
            "obs" -> _uiState.update { it.copy(observations = value) }
            "date" -> _uiState.update { it.copy(donationDate = value) }
            "campaign" -> _uiState.update { it.copy(selectedCampaignId = value) }
        }
    }
    fun startEdit(donation: Donation) {
        _uiState.update {
            it.copy(
                isEditMode = true,
                editingDonationId = donation.id,
                donorName = donation.donorName,
                estimatedValue = donation.estimatedValue.toString(),
                donorContact = donation.donorContact ?: "",
                observations = donation.observations ?: "",
                donationDate = reverseFormat(donation.donationDate)
            )
        }
    }
    private fun fetchUserRole() {
        viewModelScope.launch {
            getLoggedUserRoleUseCase.execute().collect { res ->
                if (res is ResultWrapper.Success) _uiState.update {
                    it.copy(userRole = res.data)
                }
            }
        }
    }
    private fun parseDate(d: String): LocalDate? = try {
        val p = d.split("-");
        LocalDate(p[0].toInt(), p[1].toInt(), p[2].toInt())
    } catch(e: Exception) { null }
    private fun reverseFormat(d: String): String = try {
        val p = d.split("/"); "${p[2]}-${p[1]}-${p[0]}"
    } catch(e: Exception) { "" }
    fun createDonation() {
        val s = _uiState.value; viewModelScope.launch {
            val date = parseDate(s.donationDate) ?: return@launch;
            createDonationUseCase.execute(
                date,
                s.donorName,
                s.estimatedValue.toDoubleOrNull() ?: 0.0,
                s.donorContact.ifBlank { null },
                s.observations.ifBlank { null },
                s.selectedCampaignId
            ).collect {
                if (it is ResultWrapper.Success) {
                    logActivityUseCase.execute("Criou doação: ${s.donorName}", "DONATION")
                    loadDonations(); clearForm()
                }
            }
        }
    }
    fun updateDonation() {
        val s = _uiState.value;
        viewModelScope.launch {
            updateDonationUseCase.execute(
                s.editingDonationId!!,
                parseDate(s.donationDate),
                s.donorName,
                s.estimatedValue.toDoubleOrNull(),
                s.donorContact,
                s.observations,
                s.selectedCampaignId
            ).collect {
                if (it is ResultWrapper.Success) {
                    logActivityUseCase.execute("Editou doação: ${s.donorName}", "DONATION")
                    loadDonations(); clearForm()
                }
            }
        }
    }
    fun deleteDonation(id: String, donorName: String) {
        viewModelScope.launch {
            deleteDonationUseCase.execute(id).collect {
                if (it is ResultWrapper.Success<*>){
                    logActivityUseCase.execute("Removeu doação: ${donorName}", "DONATION")
                    loadDonations()
                }
            }
        }
    }
}