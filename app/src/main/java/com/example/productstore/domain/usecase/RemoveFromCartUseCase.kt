package com.example.productstore.domain.usecase

import com.example.productstore.domain.repository.CartRepository

class RemoveFromCartUseCase(private val cartRepository: CartRepository) {
    suspend operator fun invoke(productId: Int) {
        cartRepository.removeFromCart(productId)
    }
}
