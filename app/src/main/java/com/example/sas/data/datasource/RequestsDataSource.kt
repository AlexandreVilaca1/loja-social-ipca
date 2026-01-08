package com.example.sas.data.datasource

import com.google.firebase.dataconnect.LocalDate
import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    suspend fun listRequests(limit: Int, offset: Int): List<ListRequestsQuery.Data.RequestsItem> {
        val result = connector.listRequests.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.requests
    }

    suspend fun getRequestById(id: String): GetRequestByIdQuery.Data.Request? {
        val result = connector.getRequestById.execute(UUID.fromString(id))
        return result.data.request
    }

    suspend fun listRequestsByBeneficiary(
        beneficiaryId: String,
        limit: Int,
        offset: Int
    ): List<ListRequestsByBeneficiaryQuery.Data.RequestsItem> {
        val result = connector.listRequestsByBeneficiary.execute(UUID.fromString(beneficiaryId)) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.requests
    }

    suspend fun searchRequests(
        searchTerm: String?,
        limit: Int,
        offset: Int
    ): List<SearchRequestsQuery.Data.RequestsItem> {
        val result = connector.searchRequests.execute {
            this.searchTerm = searchTerm
            this.limit = limit
            this.offset = offset
        }
        return result.data.requests
    }

    suspend fun createRequest(
        beneficiaryId: String,
        requestDate: LocalDate,
        description: String?,
        statusId: String?
    ): String {
        val result = connector.createRequest.execute(
            beneficiaryId = UUID.fromString(beneficiaryId),
            requestDate = requestDate
        ) {
            this.description = description
            this.statusId = statusId?.let { UUID.fromString(it) }
        }
        return result.data.request_insert.id.toString()
    }

    suspend fun updateRequest(
        id: String,
        requestDate: LocalDate? = null,
        description: String? = null,
        statusId: String? = null
    ) {
        connector.updateRequest.execute(UUID.fromString(id)) {
            this.requestDate = requestDate
            this.description = description
            this.statusId = statusId?.let { UUID.fromString(it) }
        }
    }

    suspend fun updateRequestStatus(id: String, statusId: String) {
        connector.updateRequestStatus.execute(
            id = UUID.fromString(id),
            statusId = UUID.fromString(statusId)
        )
    }

    suspend fun deleteRequest(id: String) {
        connector.deleteRequest.execute(UUID.fromString(id))
    }
}