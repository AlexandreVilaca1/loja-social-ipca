package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class ListLogsUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(limit: Int? = 20, offset: Int? = 0) =
        repository.listLogs(limit, offset)
}