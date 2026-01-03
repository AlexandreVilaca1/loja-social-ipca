package com.example.sas.domain.repositories

import com.example.sas.domain.common.ResultWrapper
import com.example.sas.domain.models.Category
import com.example.sas.domain.models.CreateCategoryRequest
import com.example.sas.domain.models.UpdateCategoryRequest
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for categories.
 */
interface CategoriesRepository {

    /**
     * Lists all categories.
     */
    fun listCategories(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Category>>>

    /**
     * Lists only active categories.
     */
    fun listActiveCategories(
        limit: Int,
        offset: Int
    ): Flow<ResultWrapper<List<Category>>>

    /**
     * Gets a specific category by ID.
     */
    fun getCategoryById(id: String): Flow<ResultWrapper<Category>>

    /**
     * Creates a new category.
     */
    fun createCategory(request: CreateCategoryRequest): Flow<ResultWrapper<String>>

    /**
     * Updates an existing category.
     */
    fun updateCategory(request: UpdateCategoryRequest): Flow<ResultWrapper<Unit>>

    /**
     * Deletes a category.
     */
    fun deleteCategory(id: String): Flow<ResultWrapper<Unit>>
}