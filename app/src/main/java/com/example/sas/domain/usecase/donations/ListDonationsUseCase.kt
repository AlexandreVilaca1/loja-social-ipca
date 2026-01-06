package com.example.sas.domain.usecase.donations
import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class ListDonationsUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(limit: Int? = null, offset: Int? = null) = repository.listDonations(limit, offset)
}