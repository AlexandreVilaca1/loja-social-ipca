package com.example.sas.data.repository

import com.example.sas.data.datasource.LogsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Log
import com.example.sas.domain.models.LogType
import com.example.sas.domain.repositories.LogsRepository
import com.google.firebase.Timestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class LogsRepositoryImpl @Inject constructor(
    private val dataSource: LogsDataSource
) : LogsRepository {

    override fun createLog(description: String, logTypeId: String, userId: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createLog(description, logTypeId, userId)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLogById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getLogById(id)
            val log = item?.let {
                Log(
                    it.id.toString(),
                    it.description,
                    it.user.id.toString(),
                    it.user.name,
                    it.logType.code ?: "",
                    it.logType.name,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }
            emit(ResultWrapper.Success(log))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogs(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val logs = dataSource.listLogs(limit, offset).map {
                Log(
                    it.id.toString(),
                    it.description,
                    it.user.id.toString(),
                    it.user.name,
                    it.logType.code ?: "",
                    it.logType.name,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }
            emit(ResultWrapper.Success(logs))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar logs"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteLog(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteLog(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao deletar log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogTypes(limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val types = dataSource.listLogTypes(limit, offset).map {
                LogType(
                    it.id.toString(),
                    it.name,
                    it.code,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }
            emit(ResultWrapper.Success(types))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar tipos de log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createLogType(name: String, code: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val id = dataSource.createLogType(name, code)
            emit(ResultWrapper.Success(id))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar tipo de log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogsByDateRange(
        startDate: Timestamp,
        endDate: Timestamp,
        limit: Int?,
        offset: Int?
    ) = flow<ResultWrapper<List<Log>>> {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLogsByDateRange(startDate, endDate, limit, offset)
            val logs = items.map { item ->
                mapToLog(
                    id = item.id.toString(),
                    desc = item.description,
                    uId = item.user.id.toString(),
                    uName = item.user.name,
                    tCode = item.logType.code,
                    tName = item.logType.name,
                    cAt = formatTimestamp(item.createdAt) // <--- já estava correto
                )
            }
            emit(ResultWrapper.Success(logs))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar logs por data"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogsByLogTypeCode(logTypeCode: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLogsByLogTypeCode(logTypeCode, limit, offset)
            emit(ResultWrapper.Success(items.map {
                mapToLog(
                    it.id.toString(),
                    it.description,
                    it.user.id.toString(),
                    it.user.name,
                    it.logType.code,
                    it.logType.name,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar por código"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogsByLogType(logTypeId: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLogsByLogType(logTypeId, limit, offset)
            emit(ResultWrapper.Success(items.map {
                mapToLog(
                    it.id.toString(),
                    it.description,
                    it.user.id.toString(),
                    it.user.name,
                    it.logType.code,
                    it.logType.name,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar por tipo"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLogsByUserAndLogType(
        userId: String,
        logTypeId: String,
        limit: Int?,
        offset: Int?
    ) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLogsByUserAndLogType(userId, logTypeId, limit, offset)
            emit(ResultWrapper.Success(items.map {
                mapToLog(
                    it.id.toString(),
                    it.description,
                    it.user.id.toString(),
                    it.user.name,
                    it.logType.code,
                    it.logType.name,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar por utilizador e tipo"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchLogTypesByName(name: String, limit: Int?, offset: Int?) = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchLogTypesByName(name, limit, offset)
            emit(ResultWrapper.Success(items.map {
                LogType(
                    it.id.toString(),
                    it.name,
                    it.code,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao pesquisar tipos"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLogTypeById(id: String) = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getLogTypeById(id)
            val type = item?.let {
                LogType(
                    it.id.toString(),
                    it.name,
                    it.code,
                    formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }
            emit(ResultWrapper.Success(type))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar tipo"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateLog(id: String, description: String?, logTypeId: String?, userId: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateLog(id, description, logTypeId, userId)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar log"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateLogType(id: String, name: String?, code: String?) = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateLogType(id, name, code)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar tipo"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLogTypeByCode(code: String): Flow<ResultWrapper<LogType?>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getLogTypeByCode(code)
            val logType = item?.let {
                LogType(
                    id = it.id.toString(),
                    name = it.name,
                    code = it.code,
                    createdAt = formatTimestamp(it.createdAt) // <--- alterado aqui
                )
            }
            emit(ResultWrapper.Success(logType))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar tipo de log"))
        }
    }.flowOn(Dispatchers.IO)

    private fun mapToLog(
        id: String,
        desc: String,
        uId: String,
        uName: String,
        tCode: String?,
        tName: String,
        cAt: String
    ): Log {
        return Log(id, desc, uId, uName, tCode ?: "", tName, cAt)
    }

    private fun formatTimestamp(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(date)
    }
}
