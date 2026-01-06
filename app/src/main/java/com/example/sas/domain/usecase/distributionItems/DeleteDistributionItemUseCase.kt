package com.example.sas.domain.usecase.distributionItems

import com.example.sas.domain.common.ResultWrapper
import com.google.firebase.Firebase
import com.google.firebase.functions.functions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Use case for deleting a distribution item.
 * Chama Cloud Function que DEVOLVE ao stock automaticamente.
 */
class DeleteDistributionItemUseCase @Inject constructor() {

    private val functions = Firebase.functions("europe-west1")

    /**
     * Executes the use case to delete a distribution item.
     * A Cloud Function cuida de:
     * - Buscar quantidade do item
     * - Deletar o item
     * - DEVOLVER ao stock atomicamente
     *
     * @param distributionId UUID of the distribution
     * @param lotId UUID of the lot
     * @return Flow with result wrapper indicating success or failure
     */
    fun execute(
        distributionId: String,
        lotId: String
    ): Flow<ResultWrapper<Unit>> = flow {
        require(distributionId.isNotBlank()) { "ID da distribuição é obrigatório" }
        require(lotId.isNotBlank()) { "ID do lote é obrigatório" }

        emit(ResultWrapper.Loading())

        try {
            val data = hashMapOf(
                "action" to "DELETE",
                "distributionId" to distributionId,
                "lotId" to lotId
            )

            functions
                .getHttpsCallable("manageDistributionItem")
                .call(data)
                .await()

            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao deletar item de distribuição"))
        }
    }
}

