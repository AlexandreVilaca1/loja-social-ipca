package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class UpdateLogTypeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(id: String, name: String? = null, code: String? = null) =
        repository.updateLogType(id, name, code)
}