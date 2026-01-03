package com.example.sas.domain.usecase.products

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Product
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    fun execute(request: com.example.sas.domain.models.CreateProductRequest): Flow<ResultWrapper<String>> {
        // Business validations
        require(request.name.isNotBlank()) { "Nome do produto é obrigatório" }
        require(request.name.length >= 3) { "Nome deve ter pelo menos 3 caracteres" }
        require(request.unitOfMeasure.isNotBlank()) { "Unidade de medida é obrigatória" }

        return repository.createProduct(request)
    }
}