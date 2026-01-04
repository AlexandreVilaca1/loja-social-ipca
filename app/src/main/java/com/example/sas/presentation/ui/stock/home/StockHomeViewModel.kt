package com.example.sas.presentation.ui.stock.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.products.ListProductsUseCase
import com.example.sas.domain.usecase.lots.ListLotsUseCase
import com.example.sas.domain.usecase.categories.ListCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StockHomeUiState(
    val totalProducts: Int = 0,
    val activeProducts: Int = 0,
    val totalLots: Int = 0,
    val lotsExpiringSoon: Int = 0,
    val lotsExpired: Int = 0,
    val totalCategories: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class StockHomeViewModel @Inject constructor(
    private val listProductsUseCase: ListProductsUseCase,
    private val listLotsUseCase: ListLotsUseCase,
    private val listCategoriesUseCase: ListCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockHomeUiState())
    val uiState: StateFlow<StockHomeUiState> = _uiState.asStateFlow()

    init {
        loadStockSummary()
    }

    fun loadStockSummary() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            // Load products
            listProductsUseCase.execute(limit = 1000, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val products = result.data
                        _uiState.value = _uiState.value.copy(
                            totalProducts = products?.size ?: 0,
                            activeProducts = products?.count { it.isActive } ?: 0
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message)
                    }
                    else -> { }
                }
            }

            // Load lots
            listLotsUseCase.execute(limit = 1000, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val lots = result.data

                        val expiringSoon = lots!!.count { lot ->
                            isExpiringSoon(lot.expirationDate)
                        }

                        val expired = lots.count { lot ->
                            isExpired(lot.expirationDate)
                        }

                        _uiState.value = _uiState.value.copy(
                            totalLots = lots.size,
                            lotsExpiringSoon = expiringSoon,
                            lotsExpired = expired
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message)
                    }
                    else -> { }
                }
            }

            // Load categories
            listCategoriesUseCase.execute(limit = 1000, offset = 0).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            totalCategories = result.data!!.size,
                            isLoading = false
                        )
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    else -> { }
                }
            }
        }
    }

    private fun isExpiringSoon(expirationDateStr: String): Boolean {
        return try {
            // Parse date in format yyyy/MM/dd
            val parts = expirationDateStr.split("/")
            if (parts.size != 3) return false

            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()

            // Get today's date
            val calendar = java.util.Calendar.getInstance()
            val todayYear = calendar.get(java.util.Calendar.YEAR)
            val todayMonth = calendar.get(java.util.Calendar.MONTH) + 1
            val todayDay = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            // Calculate days until expiration (simplified)
            val expirationDays = year * 365 + month * 30 + day
            val todayDays = todayYear * 365 + todayMonth * 30 + todayDay
            val daysUntil = expirationDays - todayDays

            daysUntil in 1..30 // Expiring in 30 days
        } catch (e: Exception) {
            false
        }
    }

    private fun isExpired(expirationDateStr: String): Boolean {
        return try {
            // Parse date in format yyyy/MM/dd
            val parts = expirationDateStr.split("/")
            if (parts.size != 3) return false

            val year = parts[0].toInt()
            val month = parts[1].toInt()
            val day = parts[2].toInt()

            // Get today's date
            val calendar = java.util.Calendar.getInstance()
            val todayYear = calendar.get(java.util.Calendar.YEAR)
            val todayMonth = calendar.get(java.util.Calendar.MONTH) + 1
            val todayDay = calendar.get(java.util.Calendar.DAY_OF_MONTH)

            // Check if expired
            if (year < todayYear) return true
            if (year == todayYear && month < todayMonth) return true
            if (year == todayYear && month == todayMonth && day < todayDay) return true

            false
        } catch (e: Exception) {
            false
        }
    }

    fun refresh() {
        loadStockSummary()
    }
}