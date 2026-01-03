package com.example.sas.data.datasource

import com.google.firebase.dataconnect.generated.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data source for products using Firebase Data Connect.
 * Handles direct communication with Firebase backend for product management.
 */
@Singleton
class ProductsDataSource @Inject constructor() {

    private val connector: SasConnectorConnector
        get() = SasConnectorConnector.instance

    /**
     * Lists all products from Firebase Data Connect.
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of all products with category information
     */
    suspend fun listProducts(
        limit: Int,
        offset: Int
    ): List<ListProductsQuery.Data.ProductsItem> {
        val result = connector.listProducts.execute {
            this.limit = limit
            this.offset = offset
        }
        return result.data.products
    }

    /**
     * Lists products by category ID.
     * @param categoryId UUID of the category
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of products for the specified category
     */
    suspend fun listProductsByCategory(
        categoryId: String,
        limit: Int,
        offset: Int
    ): List<ListProductsByCategoryQuery.Data.ProductsItem> {
        val result = connector.listProductsByCategory.execute(
            categoryId = UUID.fromString(categoryId)
        ) {
            this.limit = limit
            this.offset = offset
        }
        return result.data.products
    }

    /**
     * Searches products by search term (name or description).
     * @param searchTerm Search term to filter products
     * @param limit Maximum number of results to return
     * @param offset Number of results to skip (for pagination)
     * @return List of products matching the search criteria
     */
    suspend fun searchProducts(
        searchTerm: String?,
        limit: Int,
        offset: Int
    ): List<SearchProductsQuery.Data.ProductsItem> {
        val result = connector.searchProducts.execute {
            this.searchTerm = searchTerm
            this.limit = limit
            this.offset = offset
        }
        return result.data.products
    }

    /**
     * Gets a specific product by ID.
     * @param id UUID of the product
     * @return Product details with category information, or null if not found
     */
    suspend fun getProductById(id: String): GetProductByIdQuery.Data.Product? {
        val result = connector.getProductById.execute(
            id = UUID.fromString(id)
        )
        return result.data.product
    }

    /**
     * Creates a new product in Firebase Data Connect.
     * @param name Name of the product
     * @param unitOfMeasure Unit of measure (e.g., "kg", "litros", "unidades")
     * @param isActive Whether the product is active
     * @param description Optional description of the product
     * @param categoryId Optional UUID of the associated category
     * @return The UUID of the created product
     */
    suspend fun createProduct(
        name: String,
        unitOfMeasure: String,
        isActive: Boolean,
        description: String?,
        categoryId: String?
    ): String {
        val result = connector.createProduct.execute(
            name = name,
            unitOfMeasure = unitOfMeasure,
            isActive = isActive
        ) {
            this.description = description
            this.categoryId = categoryId?.let { CategoryKey(UUID.fromString(it)) }
        }
        return result.data.product_insert.id.toString()
    }

    /**
     * Updates an existing product in Firebase Data Connect.
     * All parameters except id are optional.
     * @param id UUID of the product to update
     * @param name New product name (optional)
     * @param unitOfMeasure New unit of measure (optional)
     * @param isActive New active status (optional)
     * @param description New description (optional)
     * @param categoryId New category ID (optional)
     */
    suspend fun updateProduct(
        id: String,
        name: String? = null,
        unitOfMeasure: String? = null,
        isActive: Boolean? = null,
        description: String? = null,
        categoryId: String? = null
    ) {
        connector.updateProduct.execute(
            id = UUID.fromString(id)
        ) {
            this.name = name
            this.unitOfMeasure = unitOfMeasure
            this.isActive = isActive
            this.description = description
            this.categoryId = categoryId?.let { CategoryKey(UUID.fromString(it)) }
        }
    }

    /**
     * Deletes a product from Firebase Data Connect.
     * @param id UUID of the product to delete
     */
    suspend fun deleteProduct(id: String) {
        connector.deleteProduct.execute(
            id = UUID.fromString(id)
        )
    }
}