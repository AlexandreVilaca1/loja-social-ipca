package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class GetLogByIdUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(id: String) = repository.getLogById(id)
}