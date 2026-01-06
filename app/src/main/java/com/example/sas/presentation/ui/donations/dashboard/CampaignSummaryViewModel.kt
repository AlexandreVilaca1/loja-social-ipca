package com.example.sas.presentation.ui.campaigns.summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.campaigns.ListCampaignsUseCase
import com.example.sas.domain.usecase.donations.ListDonationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CampaignSummaryUiState(
    val totalCampaigns: Int = 0,
    val runningCampaigns: Int = 0,  // "A_DECORRER"
    val finishedCampaigns: Int = 0, // "FINALIZADA"
    val pendingCampaigns: Int = 0,  // "POR_INICIAR"
    val totalDonations: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CampaignSummaryViewModel @Inject constructor(
    private val listCampaignsUseCase: ListCampaignsUseCase,
    private val listDonationsUseCase: ListDonationsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CampaignSummaryUiState())
    val uiState: StateFlow<CampaignSummaryUiState> = _uiState.asStateFlow()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            // 1. Carregar Campanhas e calcular status
            listCampaignsUseCase.execute(limit = 1000, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val campaigns = result.data ?: emptyList()
                        _uiState.value = _uiState.value.copy(
                            totalCampaigns = campaigns.size,
                            runningCampaigns = campaigns.count { it.status?.code == "A_DECORRER" },
                            finishedCampaigns = campaigns.count { it.status?.code == "FINALIZADA" },
                            pendingCampaigns = campaigns.count { it.status?.code == "POR_INICIAR" }
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message)
                    }
                    else -> {}
                }
            }

            // 2. Carregar Doações
            listDonationsUseCase.execute(limit = 1000, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            totalDonations = result.data?.size ?: 0,
                            isLoading = false
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun refresh() = loadDashboardData()
}