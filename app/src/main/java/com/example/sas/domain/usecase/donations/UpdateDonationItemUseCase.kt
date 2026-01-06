package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class UpdateDonationItemUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(
        donationId: String,
        productId: String,
        quantity: Int? = null,
        stateId: String? = null,
        obs: String? = null
    ) = repository.updateDonationItem(donationId, productId, quantity, stateId, obs)
}