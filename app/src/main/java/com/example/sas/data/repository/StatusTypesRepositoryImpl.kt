package com.example.sas.data.repository

import com.example.sas.data.datasource.StatusTypesDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.StatusType
import com.example.sas.domain.repositories.StatusTypesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of StatusTypesRepository.
 */
@Singleton
class StatusTypesRepositoryImpl @Inject constructor(
    private val dataSource: StatusTypesDataSource
) : StatusTypesRepository {

    override fun listStatusTypes(): Flow<ResultWrapper<List<StatusType>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listStatusTypes()
            val statusTypes = items.map { item ->
                StatusType(
                    id = item.id.toString(),
                    code = item.code,
                    description = item.description,
                    color = item.color
                )
            }
            emit(ResultWrapper.Success(statusTypes))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar status"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getStatusTypeByCode(code: String): Flow<ResultWrapper<StatusType?>> = flow<ResultWrapper<StatusType?>> {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getStatusTypeByCode(code)
            if (item != null) {
                val statusType = StatusType(
                    id = item.id.toString(),
                    code = item.code,
                    description = item.description,
                    color = item.color
                )
                emit(ResultWrapper.Success(statusType))
            } else {
                emit(ResultWrapper.Success(null))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar status"))
        }
    }.flowOn(Dispatchers.IO)
}

