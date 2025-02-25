package com.example.productstore.domain.repository

import com.example.productstore.domain.model.CartItem
import com.example.productstore.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addToCart(product: Product)
    suspend fun removeFromCart(productId: Int)
    fun getCartItems(): Flow<List<CartItem>>
}

