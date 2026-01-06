package com.example.sas.domain.usecase.campaigns
import com.example.sas.domain.repositories.CampaignsRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class ListCampaignsByDateRangeUseCase @Inject constructor(private val repository: CampaignsRepository) {
    fun execute(startDate: LocalDate, endDate: LocalDate, limit: Int? = null, offset: Int? = null) =
        repository.listCampaignsByDateRange(startDate, endDate, limit, offset)
}