package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class UpdateCandidacyStatusUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(id: String, statusId: String) = repository.updateCandidacyStatus(id, statusId)
}