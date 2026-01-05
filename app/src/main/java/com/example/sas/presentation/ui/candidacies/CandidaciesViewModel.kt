package com.example.sas.presentation.ui.candidacies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Candidacy
import com.example.sas.domain.usecase.candidacies.*
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CandidaciesUiState(
    val candidacies: List<Candidacy> = emptyList(),
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val error: String? = null,
    val selectedFilter: String? = "PENDENTE",
)

@HiltViewModel
class CandidaciesViewModel @Inject constructor(
    private val listCandidaciesUseCase: ListCandidaciesUseCase,
    private val listCandidaciesByStatusUseCase: ListCandidaciesByStatusUseCase,
    private val updateCandidacyStatusUseCase: UpdateCandidacyStatusUseCase,
    private val getCandidacyByIdUseCase: GetCandidacyByIdUseCase, // Adicionado private val
    private val logActivityUseCase: LogActivityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CandidaciesUiState())
    val uiState: StateFlow<CandidaciesUiState> = _uiState.asStateFlow()

    private val STATUS_APROVADO_ID = "62d0f5bb-0e05-442b-ad4b-fc60f4868be2"
    private val STATUS_REJEITADO_ID = "74744416-ea87-41bc-9bcd-b20916775d77"

    init {
        loadCandidacies()
    }

    // Função auxiliar para extrair o nome de forma segura para o Log
    private fun extractName(observations: String?, fallbackId: String): String {
        return observations?.lines()
            ?.find { it.startsWith("Nome:") }
            ?.substringAfter("Nome:")?.trim() ?: "ID: $fallbackId"
    }

    fun loadCandidacies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val filter = _uiState.value.selectedFilter
            val flow = if (filter == null) {
                listCandidaciesUseCase.execute(limit = 50, offset = 0)
            } else {
                listCandidaciesByStatusUseCase.execute(filter, limit = 50, offset = 0)
            }

            flow.collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            candidacies = result.data ?: emptyList(),
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

    fun onFilterChanged(statusCode: String?) {
        _uiState.value = _uiState.value.copy(selectedFilter = statusCode)
        loadCandidacies()
    }

    fun approveCandidacy(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isProcessing = true)

            getCandidacyByIdUseCase.execute(id).collect { candidacyResult ->
                if (candidacyResult is ResultWrapper.Success) {
                    val candidateName = extractName(candidacyResult.data?.observations, id)

                    updateCandidacyStatusUseCase.execute(id, STATUS_APROVADO_ID).collect { updateResult ->
                        if (updateResult is ResultWrapper.Success) {
                            logActivityUseCase.execute("Aprovou candidatura: $candidateName", "CANDIDACY")
                            loadCandidacies()
                        }
                        _uiState.value = _uiState.value.copy(isProcessing = false)
                    }
                } else if (candidacyResult is ResultWrapper.Error) {
                    _uiState.value = _uiState.value.copy(error = candidacyResult.message, isProcessing = false)
                }
            }
        }
    }

    fun rejectCandidacy(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isProcessing = true)

            // 1. Busca a candidatura
            getCandidacyByIdUseCase.execute(id).collect { candidacyResult ->
                if (candidacyResult is ResultWrapper.Success) {
                    val candidateName = extractName(candidacyResult.data?.observations, id)

                    // 2. Atualiza o status
                    updateCandidacyStatusUseCase.execute(id, STATUS_REJEITADO_ID).collect { updateResult ->
                        if (updateResult is ResultWrapper.Success) {
                            // 3. Log com Nome
                            logActivityUseCase.execute("Rejeitou candidatura: $candidateName", "CANDIDACY")
                            loadCandidacies()
                        }
                        _uiState.value = _uiState.value.copy(isProcessing = false)
                    }
                } else if (candidacyResult is ResultWrapper.Error) {
                    _uiState.value = _uiState.value.copy(error = candidacyResult.message, isProcessing = false)
                }
            }
        }
    }
}