package com.example.sas.data.repository

import com.example.sas.data.datasource.ProductsDataSource
import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateProductRequest
import com.example.sas.domain.models.Product
import com.example.sas.domain.models.UpdateProductRequest
import com.example.sas.domain.repositories.ProductsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of ProductsRepository.
 */
@Singleton
class ProductsRepositoryImpl @Inject constructor(
    private val dataSource: ProductsDataSource
) : ProductsRepository {

    override fun listProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listProducts(limit, offset)
            val products = items.map { item ->
                Product(
                    id = item.id.toString(),
                    name = item.name,
                    unitOfMeasure = item.unitOfMeasure,
                    isActive = item.isActive,
                    description = item.description,
                    categoryId = item.category?.id?.toString(),
                    categoryName = item.category?.name,
                    categoryDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updateAt?.toString()
                )
            }
            emit(ResultWrapper.Success(products))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar produtos"))
        }
    }.flowOn(Dispatchers.IO)

    override fun listProductsByCategory(
        categoryId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.listProductsByCategory(categoryId, limit, offset)
            val products = items.map { item ->
                Product(
                    id = item.id.toString(),
                    name = item.name,
                    unitOfMeasure = item.unitOfMeasure,
                    isActive = item.isActive,
                    description = item.description,
                    categoryId = categoryId,
                    categoryName = null,
                    categoryDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updateAt?.toString()
                )
            }
            emit(ResultWrapper.Success(products))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao listar produtos da categoria"))
        }
    }.flowOn(Dispatchers.IO)

    override fun searchProducts(
        searchTerm: String?,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val items = dataSource.searchProducts(searchTerm, limit, offset)
            val products = items.map { item ->
                Product(
                    id = item.id.toString(),
                    name = item.name,
                    unitOfMeasure = item.unitOfMeasure,
                    isActive = item.isActive,
                    description = item.description,
                    categoryId = item.category?.id?.toString(),
                    categoryName = item.category?.name,
                    categoryDescription = null,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updateAt?.toString()
                )
            }
            emit(ResultWrapper.Success(products))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar produtos"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getProductById(id: String): Flow<ResultWrapper<Product>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val item = dataSource.getProductById(id)
            if (item != null) {
                val product = Product(
                    id = item.id.toString(),
                    name = item.name,
                    unitOfMeasure = item.unitOfMeasure,
                    isActive = item.isActive,
                    description = item.description,
                    categoryId = item.category?.id?.toString(),
                    categoryName = item.category?.name,
                    categoryDescription = item.category?.description,
                    createdAt = item.createdAt?.toString(),
                    updatedAt = item.updateAt?.toString()
                )
                emit(ResultWrapper.Success(product))
            } else {
                emit(ResultWrapper.Error("Produto não encontrado"))
            }
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao buscar produto"))
        }
    }.flowOn(Dispatchers.IO)

    override fun createProduct(request: CreateProductRequest): Flow<ResultWrapper<String>> = flow {
        emit(ResultWrapper.Loading())
        try {
            val productId = dataSource.createProduct(
                name = request.name,
                unitOfMeasure = request.unitOfMeasure,
                isActive = request.isActive,
                description = request.description,
                categoryId = request.categoryId
            )
            emit(ResultWrapper.Success(productId))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao criar produto"))
        }
    }.flowOn(Dispatchers.IO)

    override fun updateProduct(request: UpdateProductRequest): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.updateProduct(
                id = request.id,
                name = request.name,
                unitOfMeasure = request.unitOfMeasure,
                isActive = request.isActive,
                description = request.description,
                categoryId = request.categoryId
            )
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao atualizar produto"))
        }
    }.flowOn(Dispatchers.IO)

    override fun deleteProduct(id: String): Flow<ResultWrapper<Unit>> = flow {
        emit(ResultWrapper.Loading())
        try {
            dataSource.deleteProduct(id)
            emit(ResultWrapper.Success(Unit))
        } catch (e: Exception) {
            emit(ResultWrapper.Error(e.message ?: "Erro ao deletar produto"))
        }
    }.flowOn(Dispatchers.IO)
}
