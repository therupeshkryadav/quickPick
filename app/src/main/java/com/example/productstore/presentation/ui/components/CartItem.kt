package com.example.productstore.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.productstore.domain.model.CartItem
import com.example.productstore.presentation.viewmodel.CartViewModel

@Composable
fun CartItem(cartItem: CartItem, cartViewModel: CartViewModel, onSubtract: () -> Unit, onAdd: () -> Unit) {
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
            Text(text = "₹${cartItem.product.price} x ${cartItem.quantity}", fontSize = 12.sp, color = Color(0xFF21AB23))
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
