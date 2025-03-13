package com.example.financereport.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.financereport.presentation.components.AppTopBar
import com.example.financereport.presentation.dialogs.AddFinanceDialog
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // list of categories
                if (categories.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No categories found")
                    }
                } else {
                    LazyColumn {
                        items(categories) { category ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(category.color.toColorInt())
                                ), modifier = Modifier.padding(8.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
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
                                        text = category.name, modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            FinanceScreen()
        }
    }
}

@Composable
fun FinanceScreen() {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { showDialog = true }, containerColor = Color.LightGray
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Finance")
        }
    }

    AddFinanceDialog(isVisible = showDialog) {
        showDialog = false
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
