package com.example.financereport.presentation.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.financereport.R
import com.example.financereport.presentation.components.AnimatedCounter
import com.example.financereport.presentation.components.AppTopBar
import com.example.financereport.presentation.components.MonthPicker
import com.example.financereport.presentation.components.YearPicker
import com.example.financereport.presentation.dialogs.AddFinanceDialog
import com.example.financereport.presentation.home.viewmodel.HomeUiAction
import com.example.financereport.presentation.home.viewmodel.HomeUiEvent
import com.example.financereport.presentation.home.viewmodel.HomeUiState
import com.example.financereport.presentation.home.viewmodel.HomeViewModel
import com.example.financereport.presentation.home.viewmodel.MutableHomeUiState
import com.example.financereport.ui.theme.FinanceReportTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.channel.collect { event ->
            when (event) {
                is HomeUiEvent.OnCreateFinance -> {
                    viewModel.onCreateFinance(event.finance)
                }

                is HomeUiEvent.OnFindFinancesByMonthAndYear -> {
                    viewModel.findFinancesByMonthAndYear(event.month, event.year)
                }
            }
        }
    }

    HomeScreen(viewModel = viewModel, uiState = viewModel.uiState)
}

@Composable
fun HomeScreen(viewModel: HomeUiAction, uiState: HomeUiState) {
    val finances = uiState.finances
    var month by remember { mutableIntStateOf(uiState.month) }
    var year by remember { mutableIntStateOf(uiState.year) }

    Scaffold(topBar = { AppTopBar(title = stringResource(R.string.app_name)) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp, Alignment.End
                    )
                ) {
                    MonthPicker(selectedMonth = month, onMonthSelected = {
                        month = it
                        viewModel.findFinancesByMonthAndYear(month = month, year = year)
                    })
                    YearPicker(
                        selectedYear = year, onYearSelected = {
                            year = it
                            viewModel.findFinancesByMonthAndYear(month = month, year = year)
                        }, startYear = 2023
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // list of categories
                    if (uiState.financeTypes.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No finances found")
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Total Finances", fontSize = 18.sp)
                                AnimatedCounter(
                                    targetNumber = finances.sumOf { it.amount }.toInt(),
                                    bigNumber = true
                                )
                            }

                            uiState.financeTypes.forEach { financeType ->
                                Log.i("HomeScreen", "FinanceType: $financeType")
                                val total = finances.filter {
                                    it.category.financeType.id == financeType.id
                                }.sumOf { it.amount }
                                Log.i("HomeScreen", "Total: $total")

                                Card(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .padding(20.dp)
                                        .weight(1f)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxHeight()
                                            .fillMaxWidth()
                                            .weight(1f),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(
                                            8.dp,
                                            Alignment.CenterVertically
                                        )
                                    ) {
                                        Text(text = financeType.name, fontSize = 24.sp)
                                        AnimatedCounter(targetNumber = total.toInt())
                                    }
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
    viewModel: HomeUiAction, categories: List<Category>, financeTypes: List<FinanceTypes>
) {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp), contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add Finance")
        }
    }

    AddFinanceDialog(
        isVisible = showDialog, categories = categories, financeTypes = financeTypes
    ) { finance ->
        Log.i("HomeScreen", "Finance: $finance")
        showDialog = false
        if (finance != null) viewModel.onCreateFinance(finance = finance)
    }
}

@Preview(
    name = "Home Dark Screen",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    name = "Home Screen",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun HomeScreenPreview() {
    FinanceReportTheme {
        HomeScreen(viewModel = HomeUiAction.buildFake(), uiState = MutableHomeUiState.buildFake())
    }
}

@Preview(name = "Empty Home Screen")
@Composable
fun HomeScreenEmptyPreview() {
    HomeScreen(viewModel = HomeUiAction.buildFake(), uiState = MutableHomeUiState.buildEmptyFake())
}

@Preview(name = "Finances Empty Home Screen")
@Composable
fun HomeScreenFinancesEmptyPreview() {
    HomeScreen(
        viewModel = HomeUiAction.buildFake(),
        uiState = MutableHomeUiState.buildEmptyFinancesFake()
    )
}
