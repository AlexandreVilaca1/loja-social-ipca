package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.ListUsersQuery
import com.google.firebase.dataconnect.generated.SasConnectorConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for users using Firebase Data Connect.
 */
@Singleton
class UsersDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all users from Firebase Data Connect.
     */
    suspend fun listUsers(
        limit: Int,
        offset: Int
    ): List<ListUsersQuery.Data.UsersItem> {
        val result = connector.listUsers.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.users
    }
}

