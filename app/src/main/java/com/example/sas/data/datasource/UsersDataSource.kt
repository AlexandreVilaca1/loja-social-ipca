package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    // --- QUERIES EXISTENTES ---

    suspend fun listUsers(limit: Int?, offset: Int?): List<ListUsersQuery.Data.UsersItem> {
        val result = connector.listUsers.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.users
    }

    suspend fun getUserByUid(uid: String): GetUserByUidQuery.Data.UsersItem? {
        val result = connector.getUserByUid.execute(uid = uid)
        return result.data.users.firstOrNull()
    }

    // --- NOVAS MUTAÇÕES ---

    suspend fun createUser(uid: String, name: String, email: String, passwordHash: String, role: String): String {
        val result = connector.createUser.execute(
            uid = uid,
            name = name,
            email = email,
            passwordHash = passwordHash,
            role = role
        )
        return result.data.user_insert.id.toString()
    }

    suspend fun deleteUser(id: String) =
        connector.deleteUser.execute(UUID.fromString(id))

    suspend fun updateUser(
        id: String,
        uid: String? = null,
        name: String? = null,
        email: String? = null,
        role: String? = null
    ) = connector.updateUser.execute(UUID.fromString(id)) {
        this.uid = uid
        this.name = name
        this.email = email
        this.role = role
    }
}