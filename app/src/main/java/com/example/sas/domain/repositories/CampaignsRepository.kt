package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Campaign
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.flow.Flow

interface CampaignsRepository {
    fun createCampaign(name: String, startDate: LocalDate, endDate: LocalDate, address: String, type: String, observations: String?, userId: String?, statusId: String?): Flow<ResultWrapper<String>>
    fun updateCampaign(id: String, name: String?, startDate: LocalDate?, endDate: LocalDate?, address: String?, type: String?, observations: String?, userId: String?, statusId: String?): Flow<ResultWrapper<Unit>>
    fun updateCampaignStatus(id: String, statusId: String): Flow<ResultWrapper<Unit>>
    fun deleteCampaign(id: String): Flow<ResultWrapper<Unit>>
    fun getCampaignById(id: String): Flow<ResultWrapper<Campaign?>>
    fun listCampaigns(limit: Int?, offset: Int?): Flow<ResultWrapper<List<Campaign>>>
    fun searchCampaigns(query: String?, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Campaign>>>
    fun listCampaignsByDateRange(startDate: LocalDate, endDate: LocalDate, limit: Int?, offset: Int?): Flow<ResultWrapper<List<Campaign>>>
}