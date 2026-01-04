package com.example.sas.domain.usecase.lots

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Lot
import com.example.sas.domain.repositories.LotsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for listing lots.
 */
class ListLotsUseCase @Inject constructor(
    private val repository: LotsRepository
) {

    /**
     * Executes the use case to list lots.
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of lots
     */
    fun execute(
        limit: Int = 50,
        offset: Int = 0
    ): Flow<ResultWrapper<List<Lot>>> {
        return repository.listLots(limit, offset)
    }
}

