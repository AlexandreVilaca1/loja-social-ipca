package com.example.sas.presentation.ui.donations.items

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.usecase.auth.GetLoggedUserRoleUseCase
import com.example.sas.domain.usecase.donations.*
import com.example.sas.domain.usecase.logs.LogActivityUseCase
import com.example.sas.domain.usecase.products.GetProductByIdUseCase
import com.example.sas.domain.usecase.products.ListProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DonationItemsUiState(
    val donationId: String = "",
    val items: List<DonationItem> = emptyList(),
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val userRole: String? = null,

    // Campos do Formulário
    val selectedProductId: String? = null,
    val quantity: String = "",
    val observations: String = "",

    val isEditMode: Boolean = false
)

@HiltViewModel
class DonationItemsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val listDonationItemsUseCase: ListDonationItemsByDonationUseCase,
    private val createDonationItemUseCase: CreateDonationItemUseCase,
    private val updateDonationItemUseCase: UpdateDonationItemUseCase,
    private val deleteDonationItemUseCase: DeleteDonationItemUseCase,
    private val listProductsUseCase: ListProductsUseCase,
    private val getLoggedUserRoleUseCase: GetLoggedUserRoleUseCase,
    private val logActivityUseCase: LogActivityUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getDonationByIdUseCase: GetDonationByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DonationItemsUiState())
    val uiState: StateFlow<DonationItemsUiState> = _uiState.asStateFlow()

    init {
        // Pega o ID da doação vindo da navegação
        savedStateHandle.get<String>("donationId")?.let { id ->
            _uiState.update { it.copy(donationId = id) }
            loadInitialData(id)
        }

        fetchUserRole()
    }

    private fun loadInitialData(donationId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            launch {
                listProductsUseCase.execute(limit = 500, offset = 0).collect { res ->
                    if (res is ResultWrapper.Success) _uiState.update { it.copy(products = res.data ?: emptyList()) }
                }
            }

            loadItems(donationId)
        }
    }

    fun loadItems(donationId: String) {
        viewModelScope.launch {
            listDonationItemsUseCase.execute(donationId, 100).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        val items = result.data ?: emptyList()
                        _uiState.update { it.copy(items = items, isLoading = false) }
                    }
                    is ResultWrapper.Error -> {
                        _uiState.update { it.copy(error = result.message, isLoading = false) }
                    }
                    is ResultWrapper.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }
                }
            }
        }
    }

    fun saveItem() {
        val s = _uiState.value
        val productId = s.selectedProductId ?: return
        val qty = s.quantity.toIntOrNull() ?: 0
        var productName = "Produto Desconhecido"
        var donorName = "Doador Desconhecido"

        viewModelScope.launch {

            getProductByIdUseCase.execute(productId).collect { res ->
                if(res is ResultWrapper.Success){
                    productName = res.data?.name ?: "Produto Desconhecido"
                }
            }
            getDonationByIdUseCase.execute(s.donationId).collect { res ->
                if(res is ResultWrapper.Success){
                    donorName = res.data?.donorName ?: "Doador Desconhecido"
                }
            }
            if (s.isEditMode) {
                updateDonationItemUseCase.execute(s.donationId, productId, qty, s.observations).collect { res ->
                    if (res is ResultWrapper.Success) {
                        logActivityUseCase.execute("Editou produto: ${productName} da doação ${donorName}", "DONATION_ITEM")
                        loadItems(s.donationId)
                        clearForm()
                    }
                }
            } else {
                createDonationItemUseCase.execute(s.donationId, productId, qty, s.observations).collect { res ->
                    if (res is ResultWrapper.Success) {
                        logActivityUseCase.execute("Criou produto: ${productName} da doação ${donorName}", "DONATION_ITEM")
                        loadItems(s.donationId)
                        clearForm()
                    }
                }
            }
        }
    }

    fun deleteItem(productId: String) {
        val donationId = _uiState.value.donationId
        var productName = "Produto Desconhecido"
        var donorName = "Doador Desconhecido"

        viewModelScope.launch {
            getProductByIdUseCase.execute(productId).collect { res ->
                if(res is ResultWrapper.Success){
                    productName = res.data?.name ?: "Produto Desconhecido"
                }
            }
            getDonationByIdUseCase.execute(donationId).collect { res ->
                if(res is ResultWrapper.Success){
                    donorName = res.data?.donorName ?: "Doador Desconhecido"
                }
            }
            deleteDonationItemUseCase.execute(donationId, productId).collect { res ->
                if (res is ResultWrapper.Success<*>){
                    logActivityUseCase.execute("Removeu produto: ${productName} da doação ${donorName}", "DONATION_ITEM")
                    loadItems(donationId)
                }
            }
        }
    }

    fun startEdit(item: DonationItem) {
        _uiState.update { it.copy(
            isEditMode = true,
            selectedProductId = item.productId,
            quantity = item.quantity.toString(),
            observations = item.observations ?: ""
        )}
    }

    fun updateFormField(field: String, value: String?) {
        when(field) {
            "product" -> _uiState.update { it.copy(selectedProductId = value) }
            "quantity" -> _uiState.update { it.copy(quantity = value ?: "") }
            "obs" -> _uiState.update { it.copy(observations = value ?: "") }
        }
    }

    private fun fetchUserRole() {
        viewModelScope.launch {
            getLoggedUserRoleUseCase.execute().collect { res ->
                if (res is ResultWrapper.Success) _uiState.update { it.copy(userRole = res.data) }
            }
        }
    }

    fun clearForm() {
        _uiState.update { it.copy(selectedProductId = null, quantity = "", observations = "", isEditMode = false) }
    }
}