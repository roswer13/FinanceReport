package com.example.financereport.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financereport.presentation.components.AppTopBar
import com.example.financereport.presentation.home.viewmodel.HomeUiState
import com.example.financereport.presentation.home.viewmodel.HomeViewModel
import com.example.financereport.presentation.home.viewmodel.MutableHomeUiState

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navController: NavHostController) {
    val uiState = viewModel.uiState
    HomeScreen(uiState = uiState)
}

@Composable
fun HomeScreen(uiState: HomeUiState) {
    val categories = uiState.categories
    Scaffold(topBar = { AppTopBar(title = "Home") }) { paddingValues ->
        // list of categories
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (categories.isEmpty()) {
                Text(
                    text = "No categories found", modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn {
                    items(categories) { category ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(category.color.toColorInt())
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Column (horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Image(
                                    painter = painterResource(id = category.icon),
                                    contentDescription = category.name,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .size(25.dp)
                                        .clip(CircleShape)
                                        .background(Color.LightGray)
                                )
                                Text(
                                    text = category.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = MutableHomeUiState.buildFake())
}

@Preview
@Composable
fun HomeScreenDarkPreview(darkTheme: Boolean = true) {
    HomeScreen(uiState = MutableHomeUiState.buildFake())
}
