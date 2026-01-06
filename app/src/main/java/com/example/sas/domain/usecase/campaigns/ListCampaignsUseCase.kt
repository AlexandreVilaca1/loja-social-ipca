package com.example.sas.domain.usecase.campaigns
import com.example.sas.domain.repositories.CampaignsRepository
import javax.inject.Inject

class ListCampaignsUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(limit: Int? = null, offset: Int? = null) = repository.listCampaigns(limit, offset)
}