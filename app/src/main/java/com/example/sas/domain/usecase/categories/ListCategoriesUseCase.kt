package com.example.sas.domain.usecase.categories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    fun execute(
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Category>>> {
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }
        return repository.listCategories(limit, offset)
    }
}
