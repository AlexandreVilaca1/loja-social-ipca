package com.example.sas.domain.repositories

import com.example.sas.domain.models.Beneficiary
import com.example.sas.domain.common.ResultWrapper
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for beneficiaries operations.
 * Defines the contract for data access related to beneficiaries.
 */
interface BeneficiariesRepository {

    /**
     * Lists all beneficiaries with pagination.
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of beneficiaries
     */
    fun listBeneficiaries(limit: Int, offset: Int): Flow<ResultWrapper<List<Beneficiary>>>

    /**
     * Searches beneficiaries by name or NIF.
     * @param searchTerm Search term to filter by name or NIF
     * @param limit Maximum number of results
     * @param offset Starting position for pagination
     * @return Flow with result wrapper containing list of beneficiaries
     */
    fun searchBeneficiaries(searchTerm: String, limit: Int, offset: Int): Flow<ResultWrapper<List<Beneficiary>>>

    /**
     * Creates a new beneficiary.
     * @return Flow with result wrapper containing the UUID of created beneficiary
     */
    fun createBeneficiary(
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String? = null,
        observations: String? = null
    ): Flow<ResultWrapper<String>>

    /**
     * Updates an existing beneficiary.
     * @return Flow with result wrapper containing Unit on success
     */
    fun updateBeneficiary(
        id: String,
        fullName: String,
        studentNumber: Int,
        nif: String,
        course: String,
        isActive: Boolean,
        contactNumber: String,
        address: String? = null,
        observations: String? = null
    ): Flow<ResultWrapper<Unit>>
}



