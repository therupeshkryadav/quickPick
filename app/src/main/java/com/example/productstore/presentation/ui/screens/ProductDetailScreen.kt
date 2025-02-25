package com.example.productstore.presentation.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.productstore.R
import com.example.productstore.domain.model.Product
import com.example.productstore.domain.repository.ProductRepository
import com.example.productstore.domain.usecase.GetProductsUseCase
import com.example.productstore.presentation.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    productViewModel: ProductViewModel,
    navController: NavHostController
) {
    val product by productViewModel.getProductById(productId).observeAsState()
    val context = LocalContext.current // Correct way to get the context

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details", color = Color.Black, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(Color(0xFFC2D0DC))
            )
        }
    ) { paddingValues ->
        product?.let { item ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = item.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${item.price}", fontSize = 20.sp, color = Color(0xFF4A84CB))
                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = item.description, fontSize = 16.sp, color = Color.Gray) // ✅ Show description
//                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                      //  cartViewModel.addToCart(item) // ✅ Add to cart
                        Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show() // ✅ Corrected Toast
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006BEE))
                ) {
                    Text("Add to Cart", color = Color.White)
                }
            }
        } ?: Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductScreen() {
    val fakeRepository = FakeProductRepository()
    val fakeUseCase = GetProductsUseCase(fakeRepository)
    val fakeViewModel = ProductViewModel(fakeUseCase)

    val navController = rememberNavController()

    ProductScreen(
        navController = navController,
        viewModel = fakeViewModel,
        onProductClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewProductDetailScreen() {
    val fakeRepository = FakeProductRepository()
    val fakeUseCase = GetProductsUseCase(fakeRepository)
    val fakeViewModel = ProductViewModel(fakeUseCase)

    val navController = rememberNavController()

    ProductDetailScreen(
        productId = 1,
        productViewModel = fakeViewModel,
        navController = navController
    )
}


class FakeProductRepository : ProductRepository {
    override suspend fun getProducts(limit: Int, page: Int): List<Product> {
        return listOf(
            Product(
                id = 1,
                title = "Wireless Headphones",
                price = 59.99,
                imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
            ),
            Product(
                id = 2,
                title = "Smartwatch Series 5",
                price = 199.99,
                imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
            ),
            Product(
                id = 3,
                title = "Gaming Laptop",
                price = 999.99,
                imageUrl = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"
            )
        )
    }
}


