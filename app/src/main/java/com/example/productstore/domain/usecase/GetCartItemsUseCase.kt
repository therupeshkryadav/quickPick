package com.example.productstore.domain.usecase

import com.example.productstore.domain.model.CartItem
import com.example.productstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<CartItem>> {
        return cartRepository.getCartItems()
    }
}
