package com.example.productstore.domain.usecase

import com.example.productstore.domain.model.Product
import com.example.productstore.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(product: Product) {
        cartRepository.addToCart(product)
    }
}
