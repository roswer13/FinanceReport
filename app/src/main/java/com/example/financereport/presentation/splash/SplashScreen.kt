package com.example.financereport.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigate: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(1000)
        onNavigate()
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Splash Screen")
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigate = {})
}