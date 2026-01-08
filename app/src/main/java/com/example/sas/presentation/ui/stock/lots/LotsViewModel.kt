package com.example.sas.presentation.ui.stock.lots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateLotRequest
import com.example.sas.domain.models.Lot
import com.example.sas.domain.models.UpdateLotRequest
import com.example.sas.domain.models.Product
import com.example.sas.domain.usecase.auth.GetLoggedUserRoleUseCase
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import com.example.sas.domain.usecase.lots.*
import com.example.sas.domain.usecase.products.ListProductsUseCase
import com.example.sas.domain.usecase.products.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
    val userRole: String? = null,


    // Form fields
    val lotCode: String = "",
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
    private val searchLotsUseCase: SearchLotsUseCase,
    private val createLotUseCase: CreateLotUseCase,
    private val updateLotUseCase: UpdateLotUseCase,
    private val updateLotQuantityUseCase: UpdateLotQuantityUseCase,
    private val deleteLotUseCase: DeleteLotUseCase,
    private val listProductsUseCase: ListProductsUseCase,
    private val getLoggedUserRoleUseCase: GetLoggedUserRoleUseCase,
    private val logActivityUseCase: LogActivityUseCase,
    private val getLotByIdUseCase: GetLotByIdUseCase

) : ViewModel() {

    private val _uiState = MutableStateFlow(LotsUiState())
    val uiState: StateFlow<LotsUiState> = _uiState.asStateFlow()

    init {
        loadLots()
        loadProducts()
        fetchUserRole()
    }

    private fun fetchUserRole() {
        viewModelScope.launch {
            getLoggedUserRoleUseCase.execute().collect { result ->
                if (result is ResultWrapper.Success) {
                    _uiState.update { it.copy(userRole = result.data) }
                }
            }
        }
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
            loadLots()
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

    fun setCurrentQuantity(value: String) {
        _uiState.value = _uiState.value.copy(currentQuantity = value)
    }

    fun setEntryDate(value: String) {
        _uiState.value = _uiState.value.copy(entryDate = value)
    }

    fun setExpirationDate(value: String) {
        _uiState.value = _uiState.value.copy(expirationDate = value)
        // Auto-generate lotCode when expiration date changes
        generateLotCode()
    }

    fun setObservations(value: String) {
        _uiState.value = _uiState.value.copy(observations = value)
    }

    fun setProductId(value: String?) {
        _uiState.value = _uiState.value.copy(productId = value)
        // Auto-generate lotCode when product changes
        generateLotCode()
    }

    /**
     * Generates lot code automatically in format: 'Lote $productName $expirationDate'
     * If no expiration date, format: 'Lote $productName'
     */
    private fun generateLotCode() {
        val state = _uiState.value

        // Only generate if we have product
        if (state.productId != null) {
            val product = state.products.find { it.id == state.productId }
            if (product != null) {
                val lotCode = if (state.expirationDate.isNotBlank()) {
                    "Lote ${product.name} ${state.expirationDate}"
                } else {
                    "Lote ${product.name}"
                }
                _uiState.value = state.copy(lotCode = lotCode)
            }
        }
    }


    fun createLot() {
        val state = _uiState.value

        // Validations
        if (state.productId == null) {
            _uiState.value = state.copy(error = "Selecione um produto")
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


        // Ensure lotCode is generated
        if (state.lotCode.isBlank()) {
            _uiState.value = state.copy(error = "Erro ao gerar código do lote")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = CreateLotRequest(
                lotCode = state.lotCode,
                currentQuantity = currentQty,
                entryDate = state.entryDate,
                expirationDate = state.expirationDate.ifBlank { null },
                observations = state.observations.ifBlank { null },
                productId = state.productId
            )

            createLotUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        logActivityUseCase.execute("Criou lote: ${state.lotCode}", "STOCK_LOTS")
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            createdLotId = result.data
                        )
                        clearForm()
                        loadLots()
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
                currentQuantity = state.currentQuantity.toIntOrNull(),
                entryDate = state.entryDate.ifBlank { null },
                expirationDate = state.expirationDate.ifBlank { null },
                observations = state.observations.ifBlank { null },
                productId = state.productId
            )

            updateLotUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        logActivityUseCase.execute("Editou lote: ${state.lotCode}", "STOCK_LOTS")
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        clearForm()
                        loadLots()
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
        var lotCode = ""

        viewModelScope.launch {
            getLotByIdUseCase.execute(lotId).collect { result ->
                if(result is ResultWrapper.Success) lotCode = result.data?.lotCode ?: "Lote Desconhecido"
            }
            updateLotQuantityUseCase.execute(id = lotId, currentQuantity = newQuantity)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            logActivityUseCase.execute("Editou quantidade lote: ${lotCode}", "STOCK_LOTS")
                            loadLots()
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
            currentQuantity = lot.currentQuantity.toString(),
            entryDate = lot.entryDate,
            expirationDate = lot.expirationDate ?: "",
            observations = lot.observations ?: "",
            productId = lot.productId
        )
    }

    fun deleteLot(lotId: String, lotCode: String) {
        viewModelScope.launch {
            deleteLotUseCase.execute(lotId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        logActivityUseCase.execute("Removeu lote: ${lotCode}", "STOCK_LOTS")
                        loadLots()
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
        loadLots()
    }
}
