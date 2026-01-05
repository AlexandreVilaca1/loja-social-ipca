package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class CreateLogUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(description: String, logTypeId: String, userId: String) =
        repository.createLog(description, logTypeId, userId)
}