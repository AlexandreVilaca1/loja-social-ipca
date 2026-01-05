package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class ListCandidaciesUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(limit: Int? = null, offset: Int? = null) = repository.listCandidacies(limit, offset)
}