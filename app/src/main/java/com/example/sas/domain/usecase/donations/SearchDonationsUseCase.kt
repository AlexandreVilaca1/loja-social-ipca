package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

class SearchDonationsUseCase @Inject constructor(private val repository: DonationsRepository) {
    fun execute(query: String?, limit: Int? = null, offset: Int? = null) =
        repository.searchDonations(query, limit, offset)
}