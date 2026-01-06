package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class UpdateDonationUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(
        id: String,
        date: LocalDate? = null,
        name: String? = null,
        value: Double? = null,
        contact: String? = null,
        obs: String? = null,
        campaignId: String? = null,
        statusId: String? = null
    ) = repository.updateDonation(id, date, name, value, contact, obs, campaignId, statusId)
}