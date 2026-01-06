package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateLotUseCase @Inject constructor(
    private val repository: LotsRepository
) {
    fun execute(request: com.example.sas.domain.models.CreateLotRequest): Flow<ResultWrapper<String>> {
        // Business validations
        require(request.currentQuantity >= 0) { "Quantidade atual não pode ser negativa" }
        require(request.lotCode.isNotBlank()) { "Código do lote é obrigatório" }
        require(request.entryDate.isNotBlank()) { "Data de entrada é obrigatória" }

        return repository.createLot(request)
    }
}