package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.StatusType
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for status types operations.
 */
interface StatusTypesRepository {

    /**
     * Gets a status type by its code.
     * @param code Status code (e.g., "NAO_ENTREGUE", "ENTREGUE")
     * @return Flow with result wrapper containing the status type
     */
    fun getStatusTypeByCode(code: String): Flow<ResultWrapper<StatusType?>>
}

