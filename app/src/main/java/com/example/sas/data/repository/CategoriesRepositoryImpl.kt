package com.example.sas.data.repository

import com.example.sas.data.datasource.CategoriesDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.models.CreateCategoryRequest
import com.example.sas.domain.models.UpdateCategoryRequest
import com.example.sas.domain.repositories.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of CategoriesRepository.
 */
@Singleton
class CategoriesRepositoryImpl @Inject constructor(
    private val dataSource: CategoriesDataSource
) : CategoriesRepository {

    override fun listCategories(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Category>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listCategories(limit, offset)
            val categories = items.map { item ->
                Category(
                    id = item.id.toString(),
                    name = item.name,
                    description = item.description,
                    active = item.active ?: true,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
            }
            emit(ResultWrapper.Success(categories))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar categorias"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listActiveCategories(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Category>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listActiveCategories(limit, offset)
            val categories = items.map { item ->
                Category(
                    id = item.id.toString(),
                    name = item.name,
                    description = item.description,
                    active = item.active ?: true,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
            }
            emit(ResultWrapper.Success(categories))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar categorias ativas"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getCategoryById(id: String): Flow<ResultWrapper<Category>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getCategoryById(id)
            if (item != null) {
                val category = Category(
                    id = item.id.toString(),
                    name = item.name,
                    description = item.description,
                    active = item.active ?: true,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updatedAt?.toString()
                )
                emit(ResultWrapper.Success(category))
            } else {
                emit(ResultWrapper.Error("Categoria não encontrada"))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar categoria"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createCategory(request: CreateCategoryRequest): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val categoryId = dataSource.createCategory(
                name = request.name,
                description = request.description,
                active = request.active
            )
            emit(ResultWrapper.Success(categoryId))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar categoria"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateCategory(request: UpdateCategoryRequest): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateCategory(
                id = request.id,
                name = request.name,
                description = request.description,
                active = request.active
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar categoria"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteCategory(id: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteCategory(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao deletar categoria"))
        }
    }.flowOn(Dispatchers.IO)
}