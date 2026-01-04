package com.example.sas.presentation.ui.distributionItems

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.distributionItems.CreateDistributionItemUseCase
import com.example.sas.domain.usecase.distributionItems.ListDistributionItemsUseCase
import com.example.sas.domain.usecase.distributions.GetDistributionByIdUseCase
import com.example.sas.domain.usecase.lots.ListLotsUseCase
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
    private val createDistributionItemUseCase: CreateDistributionItemUseCase,
    private val listLotsUseCase: ListLotsUseCase,
    private val getDistributionByIdUseCase: GetDistributionByIdUseCase,
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
        loadDistribution()
        loadItems()
        loadLots()
    }

    private fun loadDistribution() {
        val id = distributionId
        if (id == null) {
            _uiState.update { it.copy(error = "ID da distribuição não encontrado") }
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "Loading distribution: $id")

            getDistributionByIdUseCase.execute(distributionId = id)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            // Loading distribution
                        }
                        is ResultWrapper.Success -> {
                            val statusCode = result.data?.statusCode
                            Log.d(TAG, "Distribution status: $statusCode")
                            _uiState.update {
                                it.copy(distributionStatusCode = statusCode)
                            }
                        }
                        is ResultWrapper.Error -> {
                            Log.e(TAG, "Failed to load distribution: ${result.message}")
                        }
                    }
                }
        }
    }

    private fun loadLots() {
        viewModelScope.launch {
            Log.d(TAG, "Loading lots...")

            listLotsUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Loading -> {
                            // Loading lots
                        }
                        is ResultWrapper.Success -> {
                            Log.d(TAG, "Loaded ${result.data?.size ?: 0} lots")
                            _uiState.update {
                                it.copy(availableLots = result.data ?: emptyList())
                            }
                        }
                        is ResultWrapper.Error -> {
                            Log.e(TAG, "Failed to load lots: ${result.message}")
                        }
                    }
                }
        }
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

    fun createDistributionItem(
        lotId: String,
        quantity: Int,
        observations: String?,
        onSuccess: () -> Unit
    ) {
        val id = distributionId
        if (id == null) {
            _uiState.update { it.copy(error = "ID da distribuição não encontrado") }
            return
        }

        viewModelScope.launch {
            Log.d(TAG, "Creating distribution item for distribution: $id, lot: $lotId")

            createDistributionItemUseCase.execute(
                distributionId = id,
                lotId = lotId,
                quantity = quantity,
                observations = observations
            ).collect { result ->
                when (result) {
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isCreating = true, error = null) }
                    }
                    is ResultWrapper.Success -> {
                        Log.d(TAG, "Distribution item created successfully")
                        _uiState.update { it.copy(isCreating = false) }
                        refreshItems()
                        onSuccess()
                    }
                    is ResultWrapper.Error -> {
                        Log.e(TAG, "Failed to create distribution item: ${result.message}")
                        _uiState.update {
                            it.copy(
                                isCreating = false,
                                error = result.message ?: "Erro ao criar item"
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

