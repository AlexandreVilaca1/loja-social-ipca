package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class CountCandidaciesByStatusUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(statusCode: String) = repository.countCandidaciesByStatus(statusCode)
}