package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.CreateProductRequest
import com.example.sas.domain.models.Product
import com.example.sas.domain.models.UpdateProductRequest
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for products.
 */
interface ProductsRepository {

    /**
     * Lists all products.
     */
    fun listProducts(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>>

    /**
     * Lists products by category.
     */
    fun listProductsByCategory(
        categoryId: String,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>>

    /**
     * Searches products by search term.
     */
    fun searchProducts(
        searchTerm: String?,
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Product>>>

    /**
     * Gets a specific product by ID.
     */
    fun getProductById(id: String): Flow<ResultWrapper<Product>>

    /**
     * Creates a new product.
     */
    fun createProduct(request: CreateProductRequest): Flow<ResultWrapper<String>>

    /**
     * Updates an existing product.
     */
    fun updateProduct(request: UpdateProductRequest): Flow<ResultWrapper<Unit>>

    /**
     * Deletes a product.
     */
    fun deleteProduct(id: String): Flow<ResultWrapper<Unit>>
}