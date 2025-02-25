package com.example.productstore.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.productstore.R
import com.example.productstore.presentation.ui.components.BottomNavigationBar
import com.example.productstore.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {
    val currentRoute = Screen.Cart.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cart", fontWeight = FontWeight.Bold, color = Color.Black) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFC2D0DC))
            )
        },
        bottomBar = { BottomNavigationBar(navController, currentRoute) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            repeat(3) { index -> // Mocking 3 items in cart
                CartItem(
                    productName = "Product $index",
                    productPrice = (index + 1) * 50,
                    productImage = R.drawable.ic_app_launcher_foreground
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            CheckoutBar()
        }
    }
}

@Composable
fun CartItem(productName: String, productPrice: Int, productImage: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = productImage),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = productName, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$$productPrice", fontSize = 12.sp, color = Color(0xFF21AB23))
        }
        IconButton(onClick = { /* Remove Item */ }) {
            Icon(Icons.Default.Delete, contentDescription = "Remove", tint = Color.Red)
        }
    }
}

@Composable
fun CheckoutBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = Color.White
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Total:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = "$150", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(R.color.teal_700))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Proceed to Checkout */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF006BEE))
            ) {
                Text("Checkout", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCartScreen() {
    CartScreen(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun PreviewCartItem() {
    CartItem(
        productName = "Sample Product",
        productPrice = 99,
        productImage = R.drawable.ic_app_launcher_foreground
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckoutBar() {
    CheckoutBar()
}