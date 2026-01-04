package com.example.sas.presentation.ui.stock.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.models.CreateCategoryRequest
import com.example.sas.domain.models.UpdateCategoryRequest
import com.example.sas.domain.usecase.categories.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,

    // Form fields
    val name: String = "",
    val description: String = "",
    val active: Boolean = true,

    // Edit mode
    val isEditMode: Boolean = false,
    val editingCategoryId: String? = null,
    val createdCategoryId: String? = null
)

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val listCategoriesUseCase: ListCategoriesUseCase,
    private val listActiveCategoriesUseCase: ListActiveCategoriesUseCase,
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            listCategoriesUseCase.execute(limit = 100, offset = 0)
                .collect { result ->
                    when (result) {
                        is ResultWrapper.Success -> {
                            _uiState.value = _uiState.value.copy(
                                categories = result.data!!,
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

    fun setDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun setActive(value: Boolean) {
        _uiState.value = _uiState.value.copy(active = value)
    }

    fun createCategory() {
        val state = _uiState.value

        if (state.name.isBlank()) {
            _uiState.value = state.copy(error = "Nome é obrigatório")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = CreateCategoryRequest(
                name = state.name,
                description = state.description.ifBlank { null },
                active = state.active
            )

            createCategoryUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            createdCategoryId = result.data
                        )
                        clearForm()
                        loadCategories()
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

    fun updateCategory() {
        val state = _uiState.value
        val categoryId = state.editingCategoryId ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val request = UpdateCategoryRequest(
                id = categoryId,
                name = state.name.ifBlank { null },
                description = state.description.ifBlank { null },
                active = state.active
            )

            updateCategoryUseCase.execute(request).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                        clearForm()
                        loadCategories()
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

    fun startEditCategory(category: Category) {
        _uiState.value = _uiState.value.copy(
            isEditMode = true,
            editingCategoryId = category.id,
            name = category.name,
            description = category.description ?: "",
            active = category.active
        )
    }

    fun deleteCategory(categoryId: String) {
        viewModelScope.launch {
            deleteCategoryUseCase.execute(categoryId).collect { result ->
                when (result) {
                    is ResultWrapper.Success -> {
                        loadCategories()
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
            description = "",
            active = true,
            isEditMode = false,
            editingCategoryId = null,
            error = null
        )
    }

    fun refreshCategories() {
        loadCategories()
    }
}