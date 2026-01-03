package com.example.sas.domain.usecase.products

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Product
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    fun execute(request: com.example.sas.domain.models.UpdateProductRequest): Flow<ResultWrapper<Unit>> {
        require(request.id.isNotBlank()) { "ID do produto é obrigatório" }

        // Validate fields if provided
        request.name?.let {
            require(it.isNotBlank()) { "Nome não pode estar vazio" }
            require(it.length >= 3) { "Nome deve ter pelo menos 3 caracteres" }
        }
        request.unitOfMeasure?.let {
            require(it.isNotBlank()) { "Unidade de medida não pode estar vazia" }
        }

        return repository.updateProduct(request)
    }
}