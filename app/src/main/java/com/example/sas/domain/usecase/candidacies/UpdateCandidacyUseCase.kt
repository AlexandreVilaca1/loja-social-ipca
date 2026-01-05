package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class UpdateCandidacyUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(id: String, applicationDate: LocalDate?, statusId: String?, observations: String?) =
        repository.updateCandidacy(id, applicationDate, statusId, observations)
}
