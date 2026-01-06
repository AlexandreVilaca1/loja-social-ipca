package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class ListDonationItemsUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(donationId: String, limit: Int? = 0, offset: Int? = 0) =
        repository.listDonationItemsByDonation(donationId, limit, offset)
}