package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class ListLogsByUserAndLogTypeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(userId: String, logTypeId: String) =
        repository.listLogsByUserAndLogType(userId, logTypeId, 50, 0)
}