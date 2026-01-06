package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class ListDonationItemsUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(donationId: String, limit: Int? = null, offset: Int? = null) =
        repository.listDonationItemsByDonation(donationId, limit, offset)
}