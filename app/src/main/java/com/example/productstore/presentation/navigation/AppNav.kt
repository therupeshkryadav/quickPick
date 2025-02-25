package com.example.productstore.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.productstore.presentation.ui.screens.CartScreen
import com.example.productstore.presentation.ui.screens.ProductDetailScreen
import com.example.productstore.presentation.ui.screens.ProductScreen
import com.example.productstore.presentation.ui.screens.ProfileScreen
import com.example.productstore.presentation.ui.screens.SplashScreen
import com.example.productstore.presentation.viewmodel.CartViewModel
import com.example.productstore.presentation.viewmodel.ProductViewModel

@Composable
fun AppNav(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }
        // Product List Screen
        composable(Screen.ProductList.route) {
            ProductScreen(
                navController = navController,
                viewModel = productViewModel,
                onProductClick = { productId: Int ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }


        composable(Screen.Cart.route) {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }


        // Product Detail Screen
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { defaultValue = "-1" })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull()
            if (productId != null && productId != -1) {
                ProductDetailScreen(
                    productId = productId,
                    productViewModel = productViewModel,
                    cartViewModel = cartViewModel,
                    navController = navController
                )
            }
        }
    }
}
