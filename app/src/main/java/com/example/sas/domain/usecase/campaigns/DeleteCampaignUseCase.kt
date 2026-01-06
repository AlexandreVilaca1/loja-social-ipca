package com.example.sas.domain.usecase.campaigns

import com.example.sas.domain.repositories.CampaignsRepository
import javax.inject.Inject

class DeleteCampaignUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(id: String) = repository.deleteCampaign(id)
}