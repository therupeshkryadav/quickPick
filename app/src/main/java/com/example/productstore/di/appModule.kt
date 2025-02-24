package com.example.productstore.di

import com.example.productstore.data.network.ProductApi
import com.example.productstore.data.repository.ProductRepositoryImpl
import com.example.productstore.domain.repository.ProductRepository
import com.example.productstore.domain.usecase.GetProductsUseCase
import com.example.productstore.presentation.viewmodel.ProductViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 4️⃣ Dependency Injection (Koin)
val appModule = module {
    // Provide OkHttp Client
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    // Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(get()) // Use the OkHttp client
            .addConverterFactory(GsonConverterFactory.create()) // Add Gson converter
            .build()
    }

    // Provide API Service
    single<ProductApi> { get<Retrofit>().create(ProductApi::class.java) }

    // Provide Repository
    single<ProductRepository> { ProductRepositoryImpl(get()) }

    // Provide Use Case
    factory { GetProductsUseCase(get()) }

    // Provide ViewModel
    viewModel { ProductViewModel(get()) }
}
