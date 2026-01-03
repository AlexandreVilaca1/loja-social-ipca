package com.example.sas.domain.usecase.categories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoryByIdUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    fun execute(id: String): Flow<ResultWrapper<Category>> {
        require(id.isNotBlank()) { "ID da categoria é obrigatório" }
        return repository.getCategoryById(id)
    }
}