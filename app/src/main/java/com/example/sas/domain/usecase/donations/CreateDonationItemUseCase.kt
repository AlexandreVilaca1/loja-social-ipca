package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class CreateDonationItemUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(donationId: String, productId: String, quantity: Int, obs: String?) =
        repository.createDonationItem(donationId, productId, quantity, obs)
}