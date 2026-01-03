package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Lot
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateLotQuantityUseCase @Inject constructor(
    private val repository: LotsRepository
) {
    fun execute(
        id: String,
        currentQuantity: Int
    ): Flow<ResultWrapper<Unit>> {
        require(id.isNotBlank()) { "ID do lote é obrigatório" }
        require(currentQuantity >= 0) { "Quantidade atual não pode ser negativa" }

        return repository.updateLotQuantity(id, currentQuantity)
    }
}