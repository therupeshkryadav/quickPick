package com.example.productstore.utils

import com.example.productstore.data.model.ProductResponse
import com.example.productstore.domain.model.Product

fun ProductResponse.toDomain() = Product(id, title,category, description, price, image)