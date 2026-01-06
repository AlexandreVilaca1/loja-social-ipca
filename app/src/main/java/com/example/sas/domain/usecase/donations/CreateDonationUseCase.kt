package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import com.google.firebase.dataconnect.LocalDate
import javax.inject.Inject

class CreateDonationUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(date: LocalDate, name: String, value: Double, contact: String?, obs: String?, campaignId: String?) =
        repository.createDonation(date, name, value, contact, obs, campaignId)
}