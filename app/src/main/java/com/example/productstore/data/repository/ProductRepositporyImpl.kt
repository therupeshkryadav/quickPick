package com.example.productstore.data.repository

import com.example.productstore.data.network.ProductApi
import com.example.productstore.domain.model.Product
import com.example.productstore.domain.repository.ProductRepository
import com.example.productstore.utils.toDomain

// Repository Implementation
class ProductRepositoryImpl(private val api: ProductApi) : ProductRepository {
    override suspend fun getProducts(limit: Int, page: Int): List<Product> {
        return api.getProducts(limit).map { it.toDomain() } // ✅ Only pass `limit`
    }
}
