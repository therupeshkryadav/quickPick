package com.example.productstore.presentation.ui.screens

import android.util.Log
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.productstore.presentation.navigation.Screen
import com.example.productstore.presentation.ui.components.BottomNavigationBar
import com.example.productstore.presentation.ui.components.ProductItem
import com.example.productstore.presentation.viewmodel.ProductViewModel
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(navController:NavHostController,viewModel: ProductViewModel, onProductClick: (Int) -> Unit) {
    val products by viewModel.products.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(initial = true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchProducts(100) // Load only 5 products
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Product Store",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFC2D0DC))
            )
        },
        bottomBar = {
            BottomNavigationBar(navController, currentRoute = Screen.ProductList.route)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                isLoading -> {
                    ShimmerEffect() // Show shimmer loading effect
                }
                errorMessage != null -> {
                    RetryButton(onRetry = { viewModel.fetchProducts(100) }, message = errorMessage)
                }
                else -> {
                    LazyColumn {
                        items(products) { product ->
                            Log.d("productXD",product.imageUrl)
                            ProductItem(product) { onProductClick(product.id) }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShimmerEffect() {
    LazyColumn {
        items(100) { // Show 5 shimmer placeholders
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray)
                        .placeholder(visible = true,
                            highlight = PlaceholderHighlight.shimmer( // ✅ Correct usage
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000),
                                repeatMode = RepeatMode.Restart
                            )
                        ))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .width(200.dp)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                            .placeholder(visible = true, highlight = PlaceholderHighlight.shimmer())
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(16.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color.LightGray)
                            .placeholder(visible = true, highlight = PlaceholderHighlight.shimmer())
                    )
                }
            }
        }
    }
}


@Composable
fun RetryButton(onRetry: () -> Unit, message: String?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message ?: "Something went wrong", color = Color.Red)
        Button(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShimmerEffect() {
    ShimmerEffect()
}



