package com.example.financereport.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.module.categories.model.Category
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
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No categories found")
                    }
                }
            }
            FinanceScreen(categories = categories)
        }
    }
}

@Composable
fun FinanceScreen(categories: List<Category>) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { showDialog = true }, containerColor = Color.LightGray
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Finance")
        }
    }

    AddFinanceDialog(isVisible = showDialog, categories = categories) {
        showDialog = false
    }
}

@Preview(name = "Home Screen")
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = MutableHomeUiState.buildFake())
}

@Preview(name = "Empty Home Screen")
@Composable
fun HomeScreenEmptyPreview() {
    HomeScreen(uiState = MutableHomeUiState.buildEmptyFake())
}
