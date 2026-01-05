package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class ListLogsByLogTypeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(logTypeId: String, limit: Int? = 50) =
        repository.listLogsByLogType(logTypeId, limit, 0)
}