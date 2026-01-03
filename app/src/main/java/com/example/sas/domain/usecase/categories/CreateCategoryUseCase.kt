package com.example.sas.domain.usecase.categories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CreateCategoryUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    fun execute(request: com.example.sas.domain.models.CreateCategoryRequest): Flow<ResultWrapper<String>> {
        // Business validations
        require(request.name.isNotBlank()) { "Nome da categoria é obrigatório" }
        require(request.name.length >= 2) { "Nome deve ter pelo menos 2 caracteres" }

        return repository.createCategory(request)
    }
}