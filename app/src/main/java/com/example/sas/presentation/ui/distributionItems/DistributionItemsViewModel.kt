package com.example.sas.presentation.ui.distributionItems

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.distributionItems.ListDistributionItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistributionItemsViewModel @Inject constructor(
    private val listDistributionItemsUseCase: ListDistributionItemsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DistributionItemsUiState())
    val uiState: StateFlow<DistributionItemsUiState> = _uiState.asStateFlow()

    private val distributionId: String? = savedStateHandle["distributionId"]
    private val distributionDate: String? = savedStateHandle["distributionDate"]

    private companion object {
        const val TAG = "DistributionItemsVM"
    }

    init {
        distributionDate?.let { date ->
            _uiState.update { it.copy(distributionDate = date) }
        }
        loadItems()
    }

    private fun loadItems() {
        val id = distributionId
        if (id == null) {
            _uiState.update { it.copy(error = "ID da distribuição não encontrado") }
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "Loading items for distribution: $id")

            listDistributionItemsUseCase.execute(distributionId = id, limit = 50, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            _uiState.update { it.copy(isLoading = true, error = null) }
                        }
                        is ResultWrapper.Success -> {
                            Log.d(TAG, "Loaded ${result.data?.size ?: 0} items")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    items = result.data ?: emptyList()
                                )
                            }
                        }
                        is ResultWrapper.Error -> {
                            Log.e(TAG, "Failed to load items: ${result.message}")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = result.message ?: "Erro ao carregar itens"
                                )
                            }
                        }
                    }
                }
        }
    }

    fun refreshItems() {
        loadItems()
    }
}

