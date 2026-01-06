package com.example.sas.domain.usecase.campaigns
import com.example.sas.domain.repositories.CampaignsRepository
import javax.inject.Inject

class SearchCampaignsUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(query: String?, limit: Int? = null, offset: Int? = null) = repository.searchCampaigns(query, limit, offset)
}