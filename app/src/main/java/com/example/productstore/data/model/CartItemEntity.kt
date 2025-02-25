package com.example.productstore.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val imageUrl: String,
    val quantity: Int
)
