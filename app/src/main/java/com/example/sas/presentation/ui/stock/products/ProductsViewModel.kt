package com.example.sas.presentation.ui.stock.products


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateProductRequest
import com.example.sas.domain.models.Product
import com.example.sas.domain.models.UpdateProductRequest
import com.example.sas.domain.models.Category
import com.example.sas.domain.usecase.products.*
import com.example.sas.domain.usecase.categories.ListActiveCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductsUiState(
    val products: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",

    // Form fields
    val name: String = "",
    val unitOfMeasure: String = "",
    val description: String = "",
    val categoryId: String? = null,
    val isActive: Boolean = true,

    // Edit mode
    val isEditMode: Boolean = false,
    val editingProductId: String? = null,
    val createdProductId: String? = null
)

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val listProductsUseCase: ListProductsUseCase,
    private val searchProductsUseCase: SearchProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val listActiveCategoriesUseCase: ListActiveCategoriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductsUiState())
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
        loadCategories()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            listProductsUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                products = result.data!!,
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

    private fun loadCategories() {
        viewModelScope.launch {
            listActiveCategoriesUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(categories = result.data!!)
                        }
                        else -> { /* Ignore errors for categories */ }
                    }
                }
        }
    }

    fun setSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)

        if (query.isBlank()) {
            loadProducts()
        } else {
            searchProducts(query)
        }
    }

    private fun searchProducts(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            searchProductsUseCase.execute(searchTerm = query, limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                products = result.data!!,
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

    fun setName(value: String) {
        _uiState.value = _uiState.value.copy(name = value)
    }

    fun setUnitOfMeasure(value: String) {
        _uiState.value = _uiState.value.copy(unitOfMeasure = value)
    }

    fun setDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun setCategoryId(value: String?) {
        _uiState.value = _uiState.value.copy(categoryId = value)
    }

    fun setIsActive(value: Boolean) {
        _uiState.value = _uiState.value.copy(isActive = value)
    }

    fun createProduct() {
        val state = _uiState.value

        if (state.name.isBlank()) {
            _uiState.value = state.copy(error = "Nome é obrigatório")
            return
        }

        if (state.unitOfMeasure.isBlank()) {
            _uiState.value = state.copy(error = "Unidade de medida é obrigatória")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = CreateProductRequest(
                name = state.name,
                unitOfMeasure = state.unitOfMeasure,
                isActive = state.isActive,
                description = state.description.ifBlank { null },
                categoryId = state.categoryId
            )

            createProductUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            createdProductId = result.data
                        )
                        clearForm()
                        loadProducts()
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

    fun updateProduct() {
        val state = _uiState.value
        val productId = state.editingProductId ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = UpdateProductRequest(
                id = productId,
                name = state.name.ifBlank { null },
                unitOfMeasure = state.unitOfMeasure.ifBlank { null },
                isActive = state.isActive,
                description = state.description.ifBlank { null },
                categoryId = state.categoryId
            )

            updateProductUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        clearForm()
                        loadProducts()
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

    fun startEditProduct(product: Product) {
        _uiState.value = _uiState.value.copy(
            isEditMode = true,
            editingProductId = product.id,
            name = product.name,
            unitOfMeasure = product.unitOfMeasure,
            description = product.description ?: "",
            categoryId = product.categoryId,
            isActive = product.isActive
        )
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.execute(productId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        loadProducts()
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
        _uiState.value = _uiState.value.copy(
            name = "",
            unitOfMeasure = "",
            description = "",
            categoryId = null,
            isActive = true,
            isEditMode = false,
            editingProductId = null,
            error = null
        )
    }

    fun refreshProducts() {
        loadProducts()
    }
}