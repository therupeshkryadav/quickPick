package com.example.productstore.domain.model

import com.example.productstore.data.model.ProductResponse

data class Product(val id: Int, val title: String, val price: Double, val imageUrl: String)

fun ProductResponse.toDomain() = Product(id, title, price, image)