package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Lot
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListLotsByProductUseCase @Inject constructor(
    private val repository: LotsRepository
) {
    fun execute(
        productId: String,
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Lot>>> {
        require(productId.isNotBlank()) { "ID do produto é obrigatório" }
        require(limit > 0) { "Limit deve ser maior que 0" }
        require(offset >= 0) { "Offset deve ser maior ou igual a 0" }
        return repository.listLotsByProduct(productId, limit, offset)
    }
}