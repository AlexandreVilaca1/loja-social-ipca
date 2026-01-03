package com.example.sas.data.repository

import com.example.sas.data.datasource.LotsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateLotRequest
import com.example.sas.domain.models.Lot
import com.example.sas.domain.models.UpdateLotRequest
import com.example.sas.domain.repositories.LotsRepository
import com.google.firebase.dataconnect.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of LotsRepository.
 */
@Singleton
class LotsRepositoryImpl @Inject constructor(
    private val dataSource: LotsDataSource
) : LotsRepository {

    override fun listLots(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLots(limit, offset)
            val lots = items.map { item ->
                Lot(
                    id = item.id.toString(),
                    initialQuantity = item.initialQuantity,
                    currentQuantity = item.currentQuantity,
                    entryDate = formatDate(item.entryDate),
                    lotCode = item.lotCode,
                    expirationDate = formatDate(item.expirationDate),
                    observations = item.observations,
                    productId = item.product?.id?.toString(),
                    productName = item.product?.name,
                    productUnitOfMeasure = item.product?.unitOfMeasure,
                    productDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
            }
            emit(ResultWrapper.Success(lots))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar lotes"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listLotsByProduct(
        productId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listLotsByProduct(productId, limit, offset)
            val lots = items.map { item ->
                Lot(
                    id = item.id.toString(),
                    initialQuantity = item.initialQuantity,
                    currentQuantity = item.currentQuantity,
                    entryDate = formatDate(item.entryDate),
                    lotCode = item.lotCode,
                    expirationDate = formatDate(item.expirationDate),
                    observations = item.observations,
                    productId = productId,
                    productName = null,
                    productUnitOfMeasure = null,
                    productDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
            }
            emit(ResultWrapper.Success(lots))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar lotes do produto"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchLots(
        lotCode: String?,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Lot>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchLots(lotCode, limit, offset)
            val lots = items.map { item ->
                Lot(
                    id = item.id.toString(),
                    initialQuantity = item.initialQuantity,
                    currentQuantity = item.currentQuantity,
                    entryDate = formatDate(item.entryDate),
                    lotCode = item.lotCode,
                    expirationDate = formatDate(item.expirationDate),
                    observations = item.observations,
                    productId = item.product?.id?.toString(),
                    productName = item.product?.name,
                    productUnitOfMeasure = null,
                    productDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
            }
            emit(ResultWrapper.Success(lots))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar lotes"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getLotById(id: String): Flow<ResultWrapper<Lot>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getLotById(id)
            if (item != null) {
                val lot = Lot(
                    id = item.id.toString(),
                    initialQuantity = item.initialQuantity,
                    currentQuantity = item.currentQuantity,
                    entryDate = formatDate(item.entryDate),
                    lotCode = item.lotCode,
                    expirationDate = formatDate(item.expirationDate),
                    observations = item.observations,
                    productId = item.product?.id?.toString(),
                    productName = item.product?.name,
                    productUnitOfMeasure = item.product?.unitOfMeasure,
                    productDescription = item.product?.description,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
                emit(ResultWrapper.Success(lot))
            } else {
                emit(ResultWrapper.Error("Lote não encontrado"))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar lote"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createLot(request: CreateLotRequest): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val lotId = dataSource.createLot(
                initialQuantity = request.initialQuantity,
                currentQuantity = request.currentQuantity,
                entryDate = parseDate(request.entryDate),
                lotCode = request.lotCode,
                expirationDate = parseDate(request.expirationDate),
                observations = request.observations,
                productId = request.productId
            )
            emit(ResultWrapper.Success(lotId))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar lote"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateLot(request: UpdateLotRequest): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateLot(
                id = request.id,
                initialQuantity = request.initialQuantity,
                currentQuantity = request.currentQuantity,
                entryDate = request.entryDate?.let { parseDate(it) },
                lotCode = request.lotCode,
                expirationDate = request.expirationDate?.let { parseDate(it) },
                observations = request.observations,
                productId = request.productId
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar lote"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateLotQuantity(
        id: String,
        currentQuantity: Int
    ): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateLotQuantity(id, currentQuantity)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar quantidade"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteLot(id: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteLot(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao deletar lote"))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Formats a LocalDate to string format yyyy/MM/dd.
     */
    private fun formatDate(date: LocalDate): String {
        return "${date.year}/${String.format("%02d", date.month)}/${String.format("%02d", date.day)}"
    }

    /**
     * Parses a date string (yyyy-MM-dd or yyyy/MM/dd) to LocalDate.
     */
    private fun parseDate(dateStr: String): LocalDate {
        val parts = dateStr.replace("/", "-").split("-")
        require(parts.size == 3) { "Data inválida: $dateStr" }
        return LocalDate(
            year = parts[0].toInt(),
            month = parts[1].toInt(),
            day = parts[2].toInt()
        )
    }
}