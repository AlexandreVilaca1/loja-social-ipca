package com.example.sas.domain.usecase.logs

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.usecase.auth.GetCurrentUserUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class LogActivityUseCase @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getLogTypeByCodeUseCase: GetLogTypeByCodeUseCase,
    private val createLogUseCase: CreateLogUseCase
) {
    /**
     * Executa o processo completo de logging.
     * Como é uma ação de auditoria, podemos tratá-la como "fire and forget"
     */
    suspend fun execute(description: String, entityCode: String) {
        // 1. Obter User (usamos .first() para pegar o primeiro valor emitido pelo Flow)
        val userResult = getCurrentUserUseCase.execute().first { it !is ResultWrapper.Loading }
        if (userResult is ResultWrapper.Success) {
            val userId = userResult.data?.id ?: return

            // 2. Obter Tipo de Log
            val typeResult = getLogTypeByCodeUseCase.execute(entityCode).first { it !is ResultWrapper.Loading }
            if (typeResult is ResultWrapper.Success) {
                val typeId = typeResult.data?.id ?: return

                // 3. Gravar o Log
                createLogUseCase.execute(description, typeId, userId).collect {
                    // Log gravado com sucesso
                }
            }
        }
    }
}