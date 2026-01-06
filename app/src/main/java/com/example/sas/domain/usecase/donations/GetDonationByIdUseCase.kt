package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class GetDonationByIdUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(id: String) = repository.getDonationById(id)
}