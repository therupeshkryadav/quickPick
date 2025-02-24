package com.example.productstore.data.network

import com.example.productstore.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int): List<ProductResponse>
}
