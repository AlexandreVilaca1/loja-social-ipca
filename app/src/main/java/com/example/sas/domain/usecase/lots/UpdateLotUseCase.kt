package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Lot
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateLotUseCase @Inject constructor(
    private val repository: LotsRepository
) {
    fun execute(request: com.example.sas.domain.models.UpdateLotRequest): Flow<ResultWrapper<Unit>> {
        require(request.id.isNotBlank()) { "ID do lote é obrigatório" }

        request.currentQuantity?.let {
            require(it >= 0) { "Quantidade atual não pode ser negativa" }
        }

        return repository.updateLot(request)
    }
}