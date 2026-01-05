package com.example.sas.domain.usecase.logs

import com.example.sas.domain.repositories.LogsRepository
import com.google.firebase.Timestamp
import javax.inject.Inject

class ListLogsByDateRangeUseCase @Inject constructor(private val repository: LogsRepository) {
    fun execute(startDate: Timestamp, endDate: Timestamp, limit: Int? = 50, offset: Int? = 0) =
        repository.listLogsByDateRange(startDate, endDate, limit, offset)
}