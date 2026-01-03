package com.example.sas.presentation.ui.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Distribution
import com.example.sas.domain.usecase.distributions.ListDistributionsUseCase
import com.example.sas.domain.usecase.distributions.ListDistributionsByStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

/**
 * ViewModel for Agendamentos (Appointments/Distributions) screen.
 */
@HiltViewModel
class AgendamentosViewModel @Inject constructor(
    private val listDistributionsUseCase: ListDistributionsUseCase,
    private val listDistributionsByStatusUseCase: ListDistributionsByStatusUseCase
) : ViewModel() {

    // State for all distributions
    private val _distributionsState = MutableStateFlow<ResultWrapper<List<Distribution>>>(ResultWrapper.Loading())
    val distributionsState: StateFlow<ResultWrapper<List<Distribution>>> = _distributionsState.asStateFlow()

    // State for today's distributions
    private val _todayDistributionsState = MutableStateFlow<List<Distribution>>(emptyList())
    val todayDistributionsState: StateFlow<List<Distribution>> = _todayDistributionsState.asStateFlow()

    // State for total count
    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    // State for loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // State for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        // Load all distributions on initialization
        loadDistributionsByStatus("NAO_ENTREGUE")
    }

    /**
     * Loads all distributions (all statuses).
     */
    fun loadAllDistributions() {
        viewModelScope.launch {
            listDistributionsUseCase.execute(
                limit = 100,
                offset = 0
            ).collect { result ->
                _distributionsState.value = result

                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = null
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false
                        _totalCount.value = result.data!!.size
                        filterTodayDistributions(result.data!!)
                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.message
                    }
                }
            }
        }
    }

    /**
     * Loads distributions by specific status.
     * @param statusCode Status code to filter by (e.g., "PENDING", "COMPLETED", "CANCELLED")
     */
    fun loadDistributionsByStatus(statusCode: String) {
        viewModelScope.launch {
            listDistributionsByStatusUseCase.execute(
                statusCode = statusCode,
                limit = 100,
                offset = 0
            ).collect { result ->
                _distributionsState.value = result

                when (result) {
                    is ResultWrapper.Loading -> {
                        _isLoading.value = true
                        _errorMessage.value = null
                    }
                    is ResultWrapper.Success -> {
                        _isLoading.value = false
                        _totalCount.value = result.data!!.size
                        filterTodayDistributions(result.data!!)
                    }
                    is ResultWrapper.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.message
                    }
                }
            }
        }
    }

    /**
     * Filters distributions to show only today's entries.
     */
    private fun filterTodayDistributions(distributions: List<Distribution>) {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
        val todayFormatted = today.format(formatter)

        val todayDistributions = distributions.filter { distribution ->
            distribution.distributionDate == todayFormatted
        }

        _todayDistributionsState.value = todayDistributions
    }

    /**
     * Loads pending distributions only.
     */
    fun loadPendingDistributions() {
        loadDistributionsByStatus("NAO_ENTREGUE")
    }

    /**
     * Loads completed distributions only.
     */
    fun loadCompletedDistributions() {
        loadDistributionsByStatus("ENTREGUE")
    }

    /**
     * Clears error message.
     */
    fun clearError() {
        _errorMessage.value = null
    }

    /**
     * Refreshes the data.
     */
    fun refresh() {
        loadAllDistributions()
    }
}