package com.example.productstore.data.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity)

    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE id = :productId LIMIT 1")
    suspend fun getCartItem(productId: Int): CartItemEntity?

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE id = :productId")
    suspend fun deleteCartItem(productId: Int)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
