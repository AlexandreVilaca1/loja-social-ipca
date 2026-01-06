package com.example.sas.domain.usecase.campaigns

import com.example.sas.domain.repositories.CampaignsRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class CreateCampaignUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(name: String, startDate: LocalDate, endDate: LocalDate, address: String, type: String, observations: String?, userId: String?, statusId: String?) =
        repository.createCampaign(name, startDate, endDate, address, type, observations, userId, statusId)
}