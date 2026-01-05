package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class UpdateLogUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(id: String, description: String? = null, logTypeId: String? = null, userId: String? = null) =
        repository.updateLog(id, description, logTypeId, userId)
}