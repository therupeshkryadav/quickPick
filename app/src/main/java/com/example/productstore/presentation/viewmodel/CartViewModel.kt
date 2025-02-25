package com.example.productstore.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productstore.domain.model.CartItem
import com.example.productstore.domain.model.Product
import com.example.productstore.domain.usecase.AddToCartUseCase
import com.example.productstore.domain.usecase.GetCartItemsUseCase
import com.example.productstore.domain.usecase.RemoveFromCartUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase
) : ViewModel() {

    private val _cartItems = MutableStateFlow(emptyList<CartItem>())
    val cartItems: StateFlow<List<CartItem>> get() = _cartItems

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            getCartItemsUseCase().collect { items ->
                _cartItems.value = items
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            addToCartUseCase(product)
        }
    }

    fun decreaseQuantity(productId: Int) {
        _cartItems.value = _cartItems.value.map { cartItem ->
            if (cartItem.product.id == productId && cartItem.quantity > 1) {
                cartItem.copy(quantity = cartItem.quantity - 1) // Only reduce quantity
            } else cartItem
        }
    }


    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            removeFromCartUseCase(productId)
        }
    }
}
