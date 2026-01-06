package com.example.sas.domain.usecase.donations

import com.example.sas.domain.repositories.DonationsRepository
import javax.inject.Inject

/**
 * Use Case para listar todos os itens pertencentes a uma doação específica.
 */
class ListDonationItemsByDonationUseCase @Inject constructor(
    private val repository: DonationsRepository
) {
    /**
     * Executa a listagem de itens de doação.
     *
     * @param donationId O UUID da doação em formato String.
     * @param limit Opcional: limite de resultados para paginação.
     * @param offset Opcional: deslocamento para paginação.
     */
    fun execute(donationId: String, limit: Int? = null, offset: Int? = null) =
        repository.listDonationItemsByDonation(donationId, limit, offset)
}