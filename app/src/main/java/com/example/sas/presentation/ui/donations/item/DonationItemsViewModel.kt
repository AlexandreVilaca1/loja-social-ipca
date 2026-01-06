package com.example.sas.presentation.ui.donations.items

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.*
import com.example.sas.domain.usecase.auth.GetLoggedUserRoleUseCase
import com.example.sas.domain.usecase.donations.*
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
    private val getLoggedUserRoleUseCase: GetLoggedUserRoleUseCase
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

            // Carregar produtos
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
            listDonationItemsUseCase.execute(donationId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> _uiState.update { it.copy(items = result.data ?: emptyList(), isLoading = false) }
                    is ResultWrapper.Error -> _uiState.update { it.copy(error = result.message, isLoading = false) }
                    is ResultWrapper.Loading -> _uiState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    // --- CRUD ---

    fun saveItem() {
        val s = _uiState.value
        val productId = s.selectedProductId ?: return
        val qty = s.quantity.toIntOrNull() ?: 0

        viewModelScope.launch {
            if (s.isEditMode) {
                updateDonationItemUseCase.execute(s.donationId, productId, qty, s.observations)
            } else {
                createDonationItemUseCase.execute(s.donationId, productId, qty, s.observations)
            }.collect { res ->
                if (res is ResultWrapper.Success) {
                    loadItems(s.donationId)
                    clearForm()
                }
            }
        }
    }

    fun deleteItem(productId: String) {
        val donationId = _uiState.value.donationId
        viewModelScope.launch {
            deleteDonationItemUseCase.execute(donationId, productId).collect { res ->
                if (res is ResultWrapper.Success<*>) loadItems(donationId)
            }
        }
    }

    // --- Helpers ---

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