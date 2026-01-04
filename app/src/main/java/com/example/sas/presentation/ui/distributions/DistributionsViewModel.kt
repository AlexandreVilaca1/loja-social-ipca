package com.example.sas.presentation.ui.distributions

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.distributions.ListDistributionsByBeneficiaryAndStatusUseCase
import com.example.sas.domain.usecase.distributions.ListDistributionsByBeneficiaryUseCase
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DistributionsUiState())
    val uiState: StateFlow<DistributionsUiState> = _uiState.asStateFlow()

    private val beneficiaryId: String? = savedStateHandle["beneficiaryId"]
    private val beneficiaryName: String? = savedStateHandle["beneficiaryName"]

    // Expor o beneficiaryId para uso na navegação
    val currentBeneficiaryId: String? get() = beneficiaryId

    private companion object {
        const val TAG = "BeneficiaryDistributionsVM"
    }

    init {
        beneficiaryName?.let { name ->
            _uiState.update { it.copy(beneficiaryName = name) }
        }
        loadDistributions()
    }

    private fun loadDistributions() {
        val id = beneficiaryId
        if (id == null) {
            _uiState.update { it.copy(error = "ID do beneficiário não encontrado") }
            return
        }

        val statusFilter = _uiState.value.selectedStatusFilter

        viewModelScope.launch {
            Log.d(TAG, "Loading distributions for beneficiary: $id, status: $statusFilter")

            val useCase = if (statusFilter != null) {
                listDistributionsByStatusUseCase.execute(id, statusFilter, 100, 0)
            } else {
                listDistributionsUseCase.execute(id, 100, 0)
            }

            useCase.collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "Loaded ${result.data?.size ?: 0} distributions")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                distributions = result.data ?: emptyList()
                            )
                        }
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Failed to load distributions: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Erro ao carregar distribuições"
                            )
                        }
                    }
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
}

