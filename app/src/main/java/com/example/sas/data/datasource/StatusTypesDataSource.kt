package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.GetStatusTypeByCodeQuery
import com.google.firebase.dataconnect.generated.SasConnectorConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for status types using Firebase Data Connect.
 */
@Singleton
class StatusTypesDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all status types from Firebase Data Connect.
     */
    suspend fun listStatusTypes(): List<com.google.firebase.dataconnect.generated.ListStatusTypesQuery.Data.StatusTypesItem> {
        val result = connector.listStatusTypes.execute {
            this.limit = 100
            this.offset = 0
        }
        return result.data.statusTypes
    }

    /**
     * Gets a status type by code from Firebase Data Connect.
     * @return The status type if found, null otherwise
     */
    suspend fun getStatusTypeByCode(code: String): GetStatusTypeByCodeQuery.Data.StatusTypesItem? {
        val result = connector.getStatusTypeByCode.execute(code = code)
        return result.data.statusTypes.firstOrNull()
    }
}

