package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import javax.inject.Inject

class RejectCandidacyUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(id: String, statusId: String, observations: String?) =
        repository.rejectCandidacy(id, statusId, observations)
}