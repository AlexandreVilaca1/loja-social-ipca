package com.example.sas.domain.usecase.products

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Product
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    fun execute(id: String): Flow<ResultWrapper<Product>> {
        require(id.isNotBlank()) { "ID do produto é obrigatório" }
        return repository.getProductById(id)
    }
}