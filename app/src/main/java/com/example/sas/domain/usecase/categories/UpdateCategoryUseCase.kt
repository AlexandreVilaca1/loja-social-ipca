package com.example.sas.domain.usecase.categories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UpdateCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    fun execute(request: com.example.sas.domain.models.UpdateCategoryRequest): Flow<ResultWrapper<Unit>> {
        require(request.id.isNotBlank()) { "ID da categoria é obrigatório" }

        // Validate fields if provided
        request.name?.let {
            require(it.isNotBlank()) { "Nome não pode estar vazio" }
            require(it.length >= 2) { "Nome deve ter pelo menos 2 caracteres" }
        }

        return repository.updateCategory(request)
    }
}