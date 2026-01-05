package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import com.google.firebase.dataconnect.LocalDate
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CandidaciesDataSource @Inject constructor() {

    private val connector: SasConnectorConnector get() = SasConnectorConnector.instance

    suspend fun createCandidacy(applicationDate: LocalDate, statusId: String?, observations: String?): String {
        val result = connector.createCandidacy.execute(applicationDate) {
            this.statusId = statusId?.let { UUID.fromString(it) }
            this.observations = observations
        }
        return result.data.candidacy_insert.id.toString()
    }

    suspend fun approveCandidacy(id: String, statusId: String, observations: String?) =
        connector.approveCandidacy.execute(UUID.fromString(id), UUID.fromString(statusId)) {
            this.observations = observations
        }

    suspend fun rejectCandidacy(id: String, statusId: String, observations: String?) =
        connector.rejectCandidacy.execute(UUID.fromString(id), UUID.fromString(statusId)) {
            this.observations = observations
        }

    suspend fun updateCandidacy(id: String, applicationDate: LocalDate?, statusId: String?, observations: String?) =
        connector.updateCandidacy.execute(UUID.fromString(id)) {
            this.applicationDate = applicationDate
            this.statusId = statusId?.let { UUID.fromString(it) }
            this.observations = observations
        }

    suspend fun updateCandidacyStatus(id: String, statusId: String) =
        connector.updateCandidacyStatus.execute(UUID.fromString(id), UUID.fromString(statusId))

    suspend fun deleteCandidacy(id: String) =
        connector.deleteCandidacy.execute(UUID.fromString(id))

    suspend fun getCandidacyById(id: String) =
        connector.getCandidacyById.execute(UUID.fromString(id)).data.candidacy

    suspend fun listCandidacies(limit: Int?, offset: Int?) =
        connector.listCandidacies.execute {
            limit?.let { this.limit = it }
            offset?.let { this.offset = it }
        }.data.candidacies

    suspend fun listCandidaciesByStatus(statusCode: String, limit: Int?, offset: Int?) =
        connector.listCandidaciesByStatus.execute(statusCode) {
            limit?.let { this.limit = it }
            offset?.let { this.offset = it }
        }.data.candidacies

    suspend fun countCandidaciesByStatus(statusCode: String) =
        connector.countCandidaciesByStatus.execute(statusCode).data.candidacies.size
}