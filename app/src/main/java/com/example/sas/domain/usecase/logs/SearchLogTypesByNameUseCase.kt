package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import javax.inject.Inject

class SearchLogTypesByNameUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(name: String) =
        repository.searchLogTypesByName(name, 20, 0)
}