package com.example.sas.domain.usecase.candidacies

import com.example.sas.domain.repositories.CandidaciesRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class CreateCandidacyUseCase @Inject constructor(private val repository: CandidaciesRepository) {
    fun execute(applicationDate: LocalDate, statusId: String?, observations: String?) =
        repository.createCandidacy(applicationDate, statusId, observations)
}