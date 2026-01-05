package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class ListLogsByLogTypeCodeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(logTypeCode: String, limit: Int? = 50) =
        repository.listLogsByLogTypeCode(logTypeCode, limit, 0)
}