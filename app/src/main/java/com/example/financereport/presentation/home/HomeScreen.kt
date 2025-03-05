package com.example.financereport.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.financereport.presentation.components.AppTopBar

@Composable
fun HomeScreen(navController: NavHostController) {
    HomeScreen()
}

@Composable
fun HomeScreen() {
    Scaffold(topBar = {AppTopBar(title = "Home")}) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Home Screen")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@Preview
@Composable
fun HomeScreenDarkPreview(darkTheme: Boolean = true) {
    HomeScreen()
}
