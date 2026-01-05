package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Candidacy
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.flow.Flow

interface CandidaciesRepository {
    fun createCandidacy(applicationDate: LocalDate, statusId: String?, observations: String?): Flow<ResultWrapper<String>>
    fun approveCandidacy(id: String, statusId: String, observations: String?): Flow<ResultWrapper<Unit>>
    fun rejectCandidacy(id: String, statusId: String, observations: String?): Flow<ResultWrapper<Unit>>
    fun updateCandidacy(id: String, applicationDate: LocalDate?, statusId: String?, observations: String?): Flow<ResultWrapper<Unit>>
    fun updateCandidacyStatus(id: String, statusId: String): Flow<ResultWrapper<Unit>>
    fun deleteCandidacy(id: String): Flow<ResultWrapper<Unit>>
    fun getCandidacyById(id: String): Flow<ResultWrapper<Candidacy?>>
    fun listCandidacies(limit: Int?, offset: Int?): Flow<ResultWrapper<List<Candidacy>>>
    fun listCandidaciesByStatus(statusCode: String, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Candidacy>>>
    fun countCandidaciesByStatus(statusCode: String): Flow<ResultWrapper<Int>>
}