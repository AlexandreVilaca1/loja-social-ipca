package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.GetBeneficiaryByIdQuery
import com.google.firebase.dataconnect.generated.GetBeneficiaryByUserIdQuery
import com.google.firebase.dataconnect.generated.ListBeneficiariesQuery
import com.google.firebase.dataconnect.generated.SearchBeneficiariesQuery
import com.google.firebase.dataconnect.generated.SasConnectorConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.instance
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for beneficiaries using Firebase Data Connect.
 * Handles direct communication with Firebase backend.
 */
@Singleton
class BeneficiariesDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    suspend fun getBeneficiaryById(id: String): GetBeneficiaryByIdQuery.Data.Beneficiary? {
        val result = connector.getBeneficiaryById.execute(id = UUID.fromString(id))
        return result.data.beneficiary
    }

    suspend fun getBeneficiaryByUserId(uid: String): GetBeneficiaryByUserIdQuery.Data.BeneficiariesItem? {
        val result = connector.getBeneficiaryByUserId.execute(uid = uid)
        return result.data.beneficiaries.firstOrNull()
    }

    /**
     * Lists beneficiaries from Firebase Data Connect.
     */
    suspend fun listBeneficiaries(
        limit: Int,
        offset: Int
    ): List<ListBeneficiariesQuery.Data.BeneficiariesItem> {
        val result = connector.listBeneficiaries.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.beneficiaries
    }

    /**
     * Searches beneficiaries by name or NIF.
     */
    suspend fun searchBeneficiaries(
        searchTerm: String,
        limit: Int,
        offset: Int
    ): List<SearchBeneficiariesQuery.Data.BeneficiariesItem> {
        val result = connector.searchBeneficiaries.execute {
            this.searchTerm = searchTerm
            this.limit = limit
            this.offset = offset
        }
        return result.data.beneficiaries
    }

    /**
     * Creates a beneficiary in Firebase Data Connect.
     * @return The UUID of the created beneficiary
     */
    suspend fun createBeneficiary(
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String?,
        observations: String?,
        uid: String?
    ): String {
        val result = connector.createBeneficiary.execute(
            fullName = fullName,
            studentNumer = studentNumber,
            nif = nif,
            course = course,
            isActive = isActive,
            contactNumber = contactNumber,

        ) {
            this.address = address
            this.observations = observations
            this.uid = uid
        }
        return result.data.beneficiary_insert.id.toString()
    }

    /**
     * Updates a beneficiary in Firebase Data Connect.
     */
    suspend fun updateBeneficiary(
        id: String,
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String?,
        observations: String?,
        uid: String?
    ) {
        connector.updateBeneficiary.execute(
            id = java.util.UUID.fromString(id)
        ) {
            this.fullName = fullName
            this.studentNumer = studentNumber
            this.nif = nif
            this.course = course
            this.isActive = isActive
            this.contactNumber = contactNumber
            this.address = address
            this.observations = observations
            this.uid = uid
        }
    }
}


