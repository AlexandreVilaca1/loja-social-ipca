package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class GetCandidacyByIdUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(id: String) = repository.getCandidacyById(id)
}
