package com.example.sas.presentation.ui.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Log
import com.example.sas.domain.models.LogType
import com.example.sas.domain.usecase.logs.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReportsUiState(
    val logs: List<Log> = emptyList(),
    val logTypes: List<LogType> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    // Filtros
    val selectedLogType: String? = null,
    val searchQuery: String = ""
)

@HiltViewModel
class ReportsViewModel @Inject constructor(
    private val listLogsUseCase: ListLogsUseCase,
    private val listLogTypesUseCase: ListLogTypesUseCase,
    private val listLogsByLogTypeCodeUseCase: ListLogsByLogTypeCodeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReportsUiState())
    val uiState: StateFlow<ReportsUiState> = _uiState.asStateFlow()

    init {
        loadInitialData()
    }

    // No ReportsViewModel.kt
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            listLogTypesUseCase.execute(limit = 100, offset = 0).collect { result ->
                if (result is ResultWrapper.Success) {
                    _uiState.value = _uiState.value.copy(
                        logTypes = result.data ?: emptyList()
                    )
                }
            }
            refreshLogs()
        }
    }

    fun refreshLogs() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            val flow = if (_uiState.value.selectedLogType != null) {
                listLogsByLogTypeCodeUseCase.execute(_uiState.value.selectedLogType!!)
            } else {
                listLogsUseCase.execute(limit = 100)
            }

            flow.collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            logs = result.data ?: emptyList(),
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

    fun onLogTypeSelected(code: String?) {
        _uiState.value = _uiState.value.copy(selectedLogType = code)
        refreshLogs()
    }

    fun exportToCsv(): String {
        val sb = StringBuilder()
        sb.append("Data;Utilizador;Tipo;Descricao\n")
        _uiState.value.logs.forEach { log ->
            sb.append("${log.createdAt};${log.userName};${log.logTypeName};${log.description}\n")
        }
        return sb.toString()
    }
}