package com.example.productstore.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.productstore.R
import com.example.productstore.domain.model.CartItem
import com.example.productstore.domain.model.Product
import com.example.productstore.presentation.navigation.Screen
import com.example.productstore.presentation.ui.components.BottomNavigationBar
import com.example.productstore.presentation.viewmodel.CartViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController, cartViewModel: CartViewModel = viewModel()) {
    val currentRoute = Screen.Cart.route
    val cartItems by cartViewModel.cartItems.collectAsState()

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
            if (cartItems.isEmpty()) {
                EmptyCartView()
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(cartItems) { cartItem ->
                        CartItemView(
                            cartItem,
                            cartViewModel,
                            onSubtract = {cartViewModel.decreaseQuantity(cartItem.product.id)},
                            onAdd = {cartViewModel.addToCart(cartItem.product)})
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
                CheckoutBar(cartItems) // Placed inside Column
            }
        }
    }
}

@Composable
fun EmptyCartView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.quick_pick), contentDescription = "Empty Cart")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your cart is empty", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
    }
}

@Composable
fun CheckoutBar(cartItems: List<CartItem>) {
    val total = cartItems.sumOf { it.product.price * it.quantity }
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
                Text(text = "$${total}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorResource(R.color.teal_700))
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

@Composable
fun CartItemView(cartItem: CartItem,cartViewModel: CartViewModel, onSubtract: () -> Unit, onAdd: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = cartItem.product.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = cartItem.product.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$${cartItem.product.price} x ${cartItem.quantity}", fontSize = 12.sp, color = Color(0xFF21AB23))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    if (cartItem.quantity > 1) {
                        onSubtract() // Decrease quantity if more than 1
                    } else {
                        cartViewModel.removeFromCart(cartItem.product.id) // Remove completely if 1
                    }
                }
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Decrease", tint = Color.Red)
            }
            Text(text = cartItem.quantity.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            IconButton(onClick = onAdd) {
                Icon(Icons.Default.Add, contentDescription = "Increase", tint = Color(0xFF031F41))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckoutBar() {
    CheckoutBar(
        listOf(
            CartItem(Product(1, "Product 1", "Category", "Description", 50.0, ""), 1)
        )
    )
}