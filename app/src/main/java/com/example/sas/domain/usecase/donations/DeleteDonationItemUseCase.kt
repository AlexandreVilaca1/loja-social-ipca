package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class DeleteDonationItemUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(donationId: String, productId: String) =
        repository.deleteDonationItem(donationId, productId)
}