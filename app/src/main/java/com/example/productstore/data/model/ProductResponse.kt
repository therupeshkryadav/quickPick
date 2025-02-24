package com.example.productstore.data.model

// Product Model (Data Layer)
data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val image: String
)