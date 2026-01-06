package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

/**
 * Use Case para listar todas as doações associadas a uma campanha específica.
 */
class ListDonationsByCampaignUseCase @Inject constructor(
    private val repository: DonationsRepository
) {
    fun execute(campaignId: String, limit: Int? = 0, offset: Int? = 0) =
        repository.listDonationsByCampaign(campaignId, limit, offset)
}