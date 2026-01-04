package com.example.sas.domain.usecase.products

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Product
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchProductsUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    fun execute(
        searchTerm: String?,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Product>>> {
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }
        return repository.searchProducts(searchTerm, limit, offset)
    }
}