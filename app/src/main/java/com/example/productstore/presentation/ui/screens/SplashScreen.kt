package com.example.productstore.presentation.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.productstore.R
import com.example.productstore.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val scale = remember { Animatable(0f) }

    // Animation
    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
        delay(1500) // Wait before navigating
        navController.navigate(Screen.ProductList.route) {
            popUpTo(Screen.Splash.route) { inclusive = true }
        }
    }

    // UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.quick_pick),
                contentDescription = "App Logo",
                modifier = Modifier
                    .scale(scale.value)
                    .size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "QuickPick", fontSize = 24.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(20.dp))

            // Loading Animation (Dots)
            LoadingAnimation()
        }
    }
}

@Composable
fun LoadingAnimation() {
    val dotSize = 20.dp
    val dotColor = Color(0xFF6200EE)
    val transition = rememberInfiniteTransition()
    val scaleValues = List(3) { index ->
        transition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1.2f,
            animationSpec = infiniteRepeatable(
                animation = tween(600, delayMillis = index * 200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        scaleValues.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .scale(scale.value)
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier.size(dotSize),
                    shape = CircleShape,
                    color = dotColor
                ) {}
            }
        }
    }
}
