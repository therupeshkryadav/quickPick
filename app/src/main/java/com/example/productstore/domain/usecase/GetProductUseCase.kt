package com.example.productstore.domain.usecase

import com.example.productstore.domain.model.Product
import com.example.productstore.domain.repository.ProductRepository

class GetProductsUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(limit: Int): List<Product> {
        return repository.getProducts(limit, 1) // Page is unused, but we pass 1 for compatibility
    }
}
