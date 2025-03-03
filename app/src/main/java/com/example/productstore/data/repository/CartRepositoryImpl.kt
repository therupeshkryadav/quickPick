package com.example.productstore.data.repository

import com.example.productstore.data.model.CartDao
import com.example.productstore.data.model.CartItemEntity
import com.example.productstore.domain.model.CartItem
import com.example.productstore.domain.model.Product
import com.example.productstore.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryImpl(private val cartDao: CartDao) : CartRepository {

    override suspend fun addToCart(product: Product) {
        val existingItem = cartDao.getCartItem(product.id)
        if (existingItem != null) {
            cartDao.updateCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
        } else {
            cartDao.insertCartItem(CartItemEntity(product.id, product.title, product.price*90, product.imageUrl, 1))
        }
    }

    override suspend fun removeFromCart(productId: Int) {
        cartDao.deleteCartItem(productId)
    }

    override fun getCartItems(): Flow<List<CartItem>> {
        return cartDao.getCartItems().map { entities ->
            entities.map { entity ->
                CartItem(Product(entity.id, entity.title, "", "", entity.price, entity.imageUrl), entity.quantity)
            }
        }
    }
}
