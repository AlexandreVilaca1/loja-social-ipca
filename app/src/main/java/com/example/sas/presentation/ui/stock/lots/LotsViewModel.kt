package com.example.sas.presentation.ui.stock.lots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateLotRequest
import com.example.sas.domain.models.Lot
import com.example.sas.domain.models.UpdateLotRequest
import com.example.sas.domain.models.Product
import com.example.sas.domain.usecase.lots.*
import com.example.sas.domain.usecase.products.ListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

data class LotsUiState(
    val lots: List<Lot> = emptyList(),
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",

    // Form fields
    val lotCode: String = "",
    val initialQuantity: String = "",
    val currentQuantity: String = "",
    val entryDate: String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
    val expirationDate: String = "",
    val observations: String = "",
    val productId: String? = null,

    // Edit mode
    val isEditMode: Boolean = false,
    val editingLotId: String? = null,
    val createdLotId: String? = null,

    // Filter
    val filterProductId: String? = null
)

@HiltViewModel
class LotsViewModel @Inject constructor(
    private val listLotsUseCase: ListLotsUseCase,
    private val listLotsByProductUseCase: ListLotsByProductUseCase,
    private val searchLotsUseCase: SearchLotsUseCase,
    private val getLotByIdUseCase: GetLotByIdUseCase,
    private val createLotUseCase: CreateLotUseCase,
    private val updateLotUseCase: UpdateLotUseCase,
    private val updateLotQuantityUseCase: UpdateLotQuantityUseCase,
    private val deleteLotUseCase: DeleteLotUseCase,
    private val listProductsUseCase: ListProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LotsUiState())
    val uiState: StateFlow<LotsUiState> = _uiState.asStateFlow()

    init {
        loadLots()
        loadProducts()
    }

    fun loadLots() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            listLotsUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                lots = result.data!!,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    fun loadLotsByProduct(productId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                filterProductId = productId
            )

            listLotsByProductUseCase.execute(productId = productId, limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                lots = result.data!!,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            listProductsUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                products = result.data!!.filter { it.isActive }
                            )
                        }
                        else -> { /* Ignore errors for products */ }
                    }
                }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)

        if (query.isBlank()) {
            if (_uiState.value.filterProductId != null) {
                loadLotsByProduct(_uiState.value.filterProductId!!)
            } else {
                loadLots()
            }
        } else {
            searchLots(query)
        }
    }

    private fun searchLots(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            searchLotsUseCase.execute(lotCode = query, limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                lots = result.data!!,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Error -> {
                            _uiState.value = _uiState.value.copy(
                                error = result.message,
                                isLoading = false
                            )
                        }
                        is ResultWrapper.Loading -> {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                        }
                    }
                }
        }
    }

    fun setLotCode(value: String) {
        _uiState.value = _uiState.value.copy(lotCode = value)
    }

    fun setInitialQuantity(value: String) {
        _uiState.value = _uiState.value.copy(initialQuantity = value)
    }

    fun setCurrentQuantity(value: String) {
        _uiState.value = _uiState.value.copy(currentQuantity = value)
    }

    fun setEntryDate(value: String) {
        _uiState.value = _uiState.value.copy(entryDate = value)
    }

    fun setExpirationDate(value: String) {
        _uiState.value = _uiState.value.copy(expirationDate = value)
    }

    fun setObservations(value: String) {
        _uiState.value = _uiState.value.copy(observations = value)
    }

    fun setProductId(value: String?) {
        _uiState.value = _uiState.value.copy(productId = value)
    }

    fun createLot() {
        val state = _uiState.value

        // Validations
        if (state.lotCode.isBlank()) {
            _uiState.value = state.copy(error = "Código do lote é obrigatório")
            return
        }

        val initialQty = state.initialQuantity.toIntOrNull()
        if (initialQty == null || initialQty <= 0) {
            _uiState.value = state.copy(error = "Quantidade inicial deve ser maior que 0")
            return
        }

        val currentQty = state.currentQuantity.toIntOrNull()
        if (currentQty == null || currentQty < 0) {
            _uiState.value = state.copy(error = "Quantidade atual não pode ser negativa")
            return
        }

        if (state.entryDate.isBlank()) {
            _uiState.value = state.copy(error = "Data de entrada é obrigatória")
            return
        }

        if (state.expirationDate.isBlank()) {
            _uiState.value = state.copy(error = "Data de validade é obrigatória")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = CreateLotRequest(
                lotCode = state.lotCode,
                initialQuantity = initialQty,
                currentQuantity = currentQty,
                entryDate = state.entryDate,
                expirationDate = state.expirationDate,
                observations = state.observations.ifBlank { null },
                productId = state.productId
            )

            createLotUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            createdLotId = result.data
                        )
                        clearForm()
                        if (state.filterProductId != null) {
                            loadLotsByProduct(state.filterProductId)
                        } else {
                            loadLots()
                        }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun updateLot() {
        val state = _uiState.value
        val lotId = state.editingLotId ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = UpdateLotRequest(
                id = lotId,
                lotCode = state.lotCode.ifBlank { null },
                initialQuantity = state.initialQuantity.toIntOrNull(),
                currentQuantity = state.currentQuantity.toIntOrNull(),
                entryDate = state.entryDate.ifBlank { null },
                expirationDate = state.expirationDate.ifBlank { null },
                observations = state.observations.ifBlank { null },
                productId = state.productId
            )

            updateLotUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        clearForm()
                        if (state.filterProductId != null) {
                            loadLotsByProduct(state.filterProductId)
                        } else {
                            loadLots()
                        }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(
                            error = result.message,
                            isLoading = false
                        )
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.value = _uiState.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun updateQuantity(lotId: String, newQuantity: Int) {
        viewModelScope.launch {
            updateLotQuantityUseCase.execute(id = lotId, currentQuantity = newQuantity)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            if (_uiState.value.filterProductId != null) {
                                loadLotsByProduct(_uiState.value.filterProductId!!)
                            } else {
                                loadLots()
                            }
                        }
                        is ResultWrapper.Error -> {
                            _uiState.value = _uiState.value.copy(error = result.message)
                        }
                        else -> { }
                    }
                }
        }
    }

    fun startEditLot(lot: Lot) {
        _uiState.value = _uiState.value.copy(
            isEditMode = true,
            editingLotId = lot.id,
            lotCode = lot.lotCode,
            initialQuantity = lot.initialQuantity.toString(),
            currentQuantity = lot.currentQuantity.toString(),
            entryDate = lot.entryDate,
            expirationDate = lot.expirationDate,
            observations = lot.observations ?: "",
            productId = lot.productId
        )
    }

    fun deleteLot(lotId: String) {
        viewModelScope.launch {
            deleteLotUseCase.execute(lotId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        if (_uiState.value.filterProductId != null) {
                            loadLotsByProduct(_uiState.value.filterProductId!!)
                        } else {
                            loadLots()
                        }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.value = _uiState.value.copy(error = result.message)
                    }
                    else -> { }
                }
            }
        }
    }

    fun cancelEdit() {
        clearForm()
    }

    private fun clearForm() {
        val today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        _uiState.value = _uiState.value.copy(
            lotCode = "",
            initialQuantity = "",
            currentQuantity = "",
            entryDate = today,
            expirationDate = "",
            observations = "",
            productId = null,
            isEditMode = false,
            editingLotId = null,
            error = null
        )
    }

    fun refreshLots() {
        if (_uiState.value.filterProductId != null) {
            loadLotsByProduct(_uiState.value.filterProductId!!)
        } else {
            loadLots()
        }
    }
}
