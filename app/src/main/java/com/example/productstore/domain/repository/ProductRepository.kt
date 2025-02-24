package com.example.productstore.domain.repository

import com.example.productstore.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(limit: Int, page: Int): List<Product>
}