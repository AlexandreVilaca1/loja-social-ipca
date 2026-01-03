package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for categories using Firebase Data Connect.
 */
@Singleton
class CategoriesDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all categories.
     */
    suspend fun listCategories(
        limit: Int,
        offset: Int
    ): List<ListCategoriesQuery.Data.CategoriesItem> {
        val result = connector.listCategories.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.categories
    }

    /**
     * Lists only active categories.
     */
    suspend fun listActiveCategories(
        limit: Int,
        offset: Int
    ): List<ListActiveCategoriesQuery.Data.CategoriesItem> {
        val result = connector.listActiveCategories.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.categories
    }

    /**
     * Gets a specific category by ID.
     */
    suspend fun getCategoryById(id: String): GetCategoryByIdQuery.Data.Category? {
        val result = connector.getCategoryById.execute(
            id = UUID.fromString(id)
        )
        return result.data.category
    }

    /**
     * Creates a new category.
     */
    suspend fun createCategory(
        name: String,
        description: String?,
        active: Boolean?
    ): String {
        val result = connector.createCategory.execute(
            name = name
        ) {
            this.description = description
            this.active = active
        }
        return result.data.category_insert.id.toString()
    }

    /**
     * Updates an existing category.
     */
    suspend fun updateCategory(
        id: String,
        name: String? = null,
        description: String? = null,
        active: Boolean? = null
    ) {
        connector.updateCategory.execute(
            id = UUID.fromString(id)
        ) {
            this.name = name
            this.description = description
            this.active = active
        }
    }

    /**
     * Deletes a category.
     */
    suspend fun deleteCategory(id: String) {
        connector.deleteCategory.execute(
            id = UUID.fromString(id)
        )
    }
}