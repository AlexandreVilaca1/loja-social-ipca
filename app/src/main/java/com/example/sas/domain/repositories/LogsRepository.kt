package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Log
import com.example.sas.domain.models.LogType
import com.google.firebase.Timestamp
import kotlinx.coroutines.flow.Flow

interface LogsRepository {
    fun createLog(description: String, logTypeId: String, userId: String): Flow<ResultWrapper<String>>
    fun getLogById(id: String): Flow<ResultWrapper<Log?>>
    fun listLogs(limit: Int?, offset: Int?): Flow<ResultWrapper<List<Log>>>
    fun deleteLog(id: String): Flow<ResultWrapper<Unit>>

    fun listLogTypes(limit: Int?, offset: Int?): Flow<ResultWrapper<List<LogType>>>
    fun createLogType(name: String, code: String?): Flow<ResultWrapper<String>>

    fun listLogsByDateRange(startDate: Timestamp, endDate: Timestamp, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Log>>>
    fun listLogsByLogTypeCode(logTypeCode: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Log>>>
    fun listLogsByLogType(logTypeId: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Log>>>
    fun listLogsByUserAndLogType(userId: String, logTypeId: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Log>>>

    fun searchLogTypesByName(name: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<LogType>>>
    fun getLogTypeById(id: String): Flow<ResultWrapper<LogType?>>

    fun getLogTypeByCode(code: String): Flow<ResultWrapper<LogType?>>

    fun updateLog(id: String, description: String?, logTypeId: String?, userId: String?): Flow<ResultWrapper<Unit>>
    fun updateLogType(id: String, name: String?, code: String?): Flow<ResultWrapper<Unit>>
}
