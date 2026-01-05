package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector get() = SasConnectorConnector.instance

    //LOGS

    suspend fun createLog(description: String, logTypeId: String, userId: String): String {
        val result = connector.createLog.execute(
            description = description,
            logTypeId = UUID.fromString(logTypeId),
            userId = UUID.fromString(userId)
        )
        return result.data.log_insert.id.toString()
    }

    suspend fun getLogById(id: String) = connector.getLogById.execute(UUID.fromString(id)).data.log

    suspend fun listLogs(limit: Int?, offset: Int?) =
        connector.listLogs.execute { this.limit = limit; this.offset = offset }.data.logs

    suspend fun listLogsByUser(userId: String, limit: Int?, offset: Int?) =
        connector.listLogsByUser.execute(UUID.fromString(userId)) { this.limit = limit; this.offset = offset }.data.logs

    suspend fun updateLog(id: String, description: String?, logTypeId: String?, userId: String?) =
        connector.updateLog.execute(UUID.fromString(id)) {
            this.description = description
            this.logTypeId = logTypeId?.let { UUID.fromString(it) }
            this.userId = userId?.let { UUID.fromString(it) }
        }

    suspend fun deleteLog(id: String) = connector.deleteLog.execute(UUID.fromString(id))

    suspend fun listLogsByDateRange(startDate: com.google.firebase.Timestamp, endDate: com.google.firebase.Timestamp, limit: Int?, offset: Int?) =
        connector.listLogsByDateRange.execute(startDate, endDate) { this.limit = limit; this.offset = offset }.data.logs

    suspend fun listLogsByLogTypeCode(logTypeCode: String, limit: Int?, offset: Int?) =
        connector.listLogsByLogTypeCode.execute(logTypeCode) { this.limit = limit; this.offset = offset }.data.logs

    suspend fun listLogsByLogType(logTypeId: String, limit: Int?, offset: Int?) =
        connector.listLogsByLogType.execute(UUID.fromString(logTypeId)) { this.limit = limit; this.offset = offset }.data.logs

    suspend fun listLogsByUserAndLogType(userId: String, logTypeId: String, limit: Int?, offset: Int?) =
        connector.listLogsByUserAndLogType.execute(UUID.fromString(userId), UUID.fromString(logTypeId)) { this.limit = limit; this.offset = offset }.data.logs

    //LOG TYPES

    suspend fun createLogType(name: String, code: String?) =
        connector.createLogType.execute(name) { this.code = code }.data.logType_insert.id.toString()

    suspend fun listLogTypes(limit: Int?, offset: Int?) =
        connector.listLogTypes.execute { this.limit = limit; this.offset = offset }.data.logTypes

    suspend fun getLogTypeByCode(code: String) =
        connector.getLogTypeByCode.execute(code).data.logTypes.firstOrNull()

    suspend fun deleteLogType(id: String) = connector.deleteLogType.execute(UUID.fromString(id))

    suspend fun searchLogTypesByName(name: String, limit: Int?, offset: Int?) =
        connector.searchLogTypesByName.execute(name) { this.limit = limit; this.offset = offset }.data.logTypes

    suspend fun getLogTypeById(id: String) =
        connector.getLogTypeById.execute(UUID.fromString(id)).data.logType

    suspend fun updateLogType(id: String, name: String?, code: String?) =
        connector.updateLogType.execute(UUID.fromString(id)) {
            this.name = name
            this.code = code
        }
}