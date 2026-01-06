package com.example.sas.domain.usecase.distributionItems

import com.example.sas.domain.common.ResultWrapper
import com.google.firebase.Firebase
import com.google.firebase.functions.functions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Use case for creating a distribution item.
 * Chama Cloud Function que gerencia toda a lógica de negócio + atualização de stock.
 */
class CreateDistributionItemUseCase @Inject constructor() {

    private val functions = Firebase.functions("europe-west1")

    /**
     * Executes the use case to create a distribution item.
     * A Cloud Function cuida de:
     * - Validar stock disponível
     * - Criar o item
     * - RETIRAR do stock atomicamente
     *
     * @param distributionId UUID of the distribution
     * @param lotId UUID of the lot
     * @param quantity Quantity to distribute
     * @param observations Optional observations
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        lotId: String,
        quantity: Int,
        observations: String?
    ): Flow<ResultWrapper<Unit>> = flow {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(lotId.isNotBlank()) { "ID do lote é obrigatório" }
        require(quantity > 0) { "Quantidade deve ser maior que zero" }

        emit(ResultWrapper.Loading())

        try {
            val data = hashMapOf(
                "action" to "CREATE",
                "distributionId" to distributionId,
                "lotId" to lotId,
                "quantity" to quantity,
                "observations" to observations
            )

            functions
                .getHttpsCallable("manageDistributionItem")
                .call(data)
                .await()

            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar item de distribuição"))
        }
    }
}

