package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Lot
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLotByIdUseCase @Inject constructor(
    private val repository: LotsRepository
) {
    fun execute(id: String): Flow<ResultWrapper<Lot>> {
        require(id.isNotBlank()) { "ID do lote é obrigatório" }
        return repository.getLotById(id)
    }
}