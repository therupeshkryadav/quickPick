package com.example.productstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.productstore.presentation.navigation.AppNav
import com.example.productstore.presentation.ui.theme.ProductStoreTheme
import com.example.productstore.presentation.viewmodel.CartViewModel
import com.example.productstore.presentation.viewmodel.ProductViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductStoreTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    val productViewModel: ProductViewModel = koinViewModel()
                    val cartViewModel: CartViewModel = koinViewModel()

                    AppNav(
                        navController = navController,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}
