package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class ListLogTypesUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(limit: Int? = null, offset: Int? = null) =
        repository.listLogTypes(limit, offset)
}