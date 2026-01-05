package com.example.sas.domain.usecase.logs

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.LogType
import com.example.sas.domain.repositories.LogsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter um tipo de log específico através do seu código.
 * Útil para operações automatizadas de log onde o ID UUID não é conhecido à partida.
 */
class GetLogTypeByCodeUseCase @Inject constructor(
    private val repository: LogsRepository
) {
    /**
     * Executa a procura do tipo de log.
     * @param code O código string (ex: "CREATE", "UPDATE", "DELETE")
     */
    fun execute(code: String): Flow<ResultWrapper<LogType?>> {
        // Validação de negócio simples
        if (code.isBlank()) {
            return kotlinx.coroutines.flow.flowOf(ResultWrapper.Error("O código não pode estar vazio"))
        }

        return repository.getLogTypeByCode(code)
    }
}