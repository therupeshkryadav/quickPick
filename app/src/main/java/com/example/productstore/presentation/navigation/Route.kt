package com.example.productstore.presentation.navigation

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object ProductList : Screen("product_list")
    data object Cart : Screen("cart")
    data object Profile : Screen("profile")
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

