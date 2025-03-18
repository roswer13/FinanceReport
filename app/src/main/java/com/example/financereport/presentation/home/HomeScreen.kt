package com.example.financereport.presentation.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.domain.module.categories.model.FinanceTypes
import com.example.financereport.presentation.components.AppTopBar
import com.example.financereport.presentation.dialogs.AddFinanceDialog
import com.example.financereport.presentation.home.viewmodel.HomeUiAction
import com.example.financereport.presentation.home.viewmodel.HomeUiEvent
import com.example.financereport.presentation.home.viewmodel.HomeUiState
import com.example.financereport.presentation.home.viewmodel.HomeViewModel
import com.example.financereport.presentation.home.viewmodel.MutableHomeUiState

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is HomeUiEvent.OnCreateFinance -> {
                    viewModel.onCreateFinance(event.finance)
                }
            }
        }
    }

    HomeScreen(viewModel = viewModel, uiState = viewModel.uiState)
}

@Composable
fun HomeScreen(viewModel: HomeUiAction, uiState: HomeUiState) {
    val finances = uiState.finances
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
                if (finances.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No finances found")
                    }
                } else {
                    LazyColumn(
                        Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(finances.size) { index ->
                            val finance = finances[index]
                            Card {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(text = finance.amount.toString())
                                    Text(text = finance.category.name)
                                    Text(text = finance.category.financeType.name)
                                    Text(text = finance.date.toString())
                                }
                            }
                        }
                    }
                }
            }
            FinanceScreen(
                viewModel = viewModel,
                categories = uiState.categories,
                financeTypes = uiState.financeTypes
            )
        }
    }
}

@Composable
fun FinanceScreen(
    viewModel: HomeUiAction,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
        FloatingActionButton(
            onClick = { showDialog = true }, containerColor = Color.LightGray
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Finance")
        }
    }

    AddFinanceDialog(
        isVisible = showDialog,
        categories = categories,
        financeTypes = financeTypes
    ) { finance ->
        Log.i("HomeScreen", "Finance: $finance")
        showDialog = false
        if (finance != null) viewModel.onCreateFinance(finance = finance)
    }
}

@Preview(name = "Home Screen")
@Composable
fun HomeScreenPreview() {
    HomeScreen(viewModel = HomeUiAction.buildFake(), uiState = MutableHomeUiState.buildFake())
}

@Preview(name = "Empty Home Screen")
@Composable
fun HomeScreenEmptyPreview() {
    HomeScreen(viewModel = HomeUiAction.buildFake(), uiState = MutableHomeUiState.buildEmptyFake())
}
