package com.example.productstore.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productstore.domain.model.Product
import com.example.productstore.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductViewModel(private val getProductsUseCase: GetProductsUseCase) : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchProducts(limit: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val result = getProductsUseCase(limit)
                _products.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Failed to load products"
            }
            _isLoading.value = false
        }
    }

    fun getProductById(productId: Int): LiveData<Product?> {
        return MutableLiveData(products.value?.find { it.id == productId })
    }

}

