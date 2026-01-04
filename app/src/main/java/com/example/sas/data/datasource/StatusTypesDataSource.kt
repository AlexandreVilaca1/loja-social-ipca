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
     * Gets a status type by its code from Firebase Data Connect.
     * @param code Status code (e.g., "NAO_ENTREGUE", "ENTREGUE")
     * @return The status type if found, null otherwise
     */
    suspend fun getStatusTypeByCode(code: String): GetStatusTypeByCodeQuery.Data.StatusTypesItem? {
        val result = connector.getStatusTypeByCode.execute(code = code)
        return result.data.statusTypes.firstOrNull()
    }
}

