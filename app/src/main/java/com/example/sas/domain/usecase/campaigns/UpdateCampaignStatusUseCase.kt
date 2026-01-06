package com.example.sas.domain.usecase.campaigns

import com.example.sas.domain.repositories.CampaignsRepository
import javax.inject.Inject

class UpdateCampaignStatusUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(id: String, statusId: String) = repository.updateCampaignStatus(id, statusId)
}