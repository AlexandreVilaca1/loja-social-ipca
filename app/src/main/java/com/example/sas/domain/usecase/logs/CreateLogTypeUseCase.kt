package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class CreateLogTypeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(name: String, code: String? = null) =
        repository.createLogType(name, code)
}