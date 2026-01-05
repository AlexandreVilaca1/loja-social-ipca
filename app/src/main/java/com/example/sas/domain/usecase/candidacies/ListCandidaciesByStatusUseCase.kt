package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class ListCandidaciesByStatusUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(statusCode: String, limit: Int? = null, offset: Int? = null) =
        repository.listCandidaciesByStatus(statusCode, limit, offset)
}