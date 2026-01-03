package com.example.sas.domain.usecase.products

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Product
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    fun execute(id: String): Flow<ResultWrapper<Unit>> {
        require(id.isNotBlank()) { "ID do produto é obrigatório" }
        return repository.deleteProduct(id)
    }
}