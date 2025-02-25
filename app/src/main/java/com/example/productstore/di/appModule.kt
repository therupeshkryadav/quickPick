package com.example.productstore.di

import android.app.Application
import androidx.room.Room
import com.example.productstore.data.model.CartDao
import com.example.productstore.data.model.CartDatabase
import com.example.productstore.data.network.ProductApi
import com.example.productstore.data.repository.CartRepositoryImpl
import com.example.productstore.data.repository.ProductRepositoryImpl
import com.example.productstore.domain.repository.CartRepository
import com.example.productstore.domain.repository.ProductRepository
import com.example.productstore.domain.usecase.AddToCartUseCase
import com.example.productstore.domain.usecase.GetCartItemsUseCase
import com.example.productstore.domain.usecase.GetProductsUseCase
import com.example.productstore.domain.usecase.RemoveFromCartUseCase
import com.example.productstore.presentation.viewmodel.CartViewModel
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

    // Provide Room Database
    single {
        Room.databaseBuilder(
            get<Application>(),
            CartDatabase::class.java,
            "cart_db"
        ).build()
    }

    // Provide CartDao
    single<CartDao> { get<CartDatabase>().cartDao() }

    // Provide CartRepository
    single<CartRepository> { CartRepositoryImpl(get()) }

    // Provide Use Cases
    factory { AddToCartUseCase(get()) }
    factory { GetCartItemsUseCase(get()) }
    factory { RemoveFromCartUseCase(get()) }

    // Provide ViewModel
    viewModel { CartViewModel(get(), get(), get()) }
}
