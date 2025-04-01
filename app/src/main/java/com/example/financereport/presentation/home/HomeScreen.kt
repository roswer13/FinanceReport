package com.example.financereport.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.R
import com.example.financereport.presentation.components.AppTopBar
import com.example.financereport.presentation.components.MonthPicker
import com.example.financereport.presentation.components.YearPicker
import com.example.financereport.presentation.dialogs.AddFinanceDialog
import com.example.financereport.presentation.dialogs.FinancesDetailDialog
import com.example.financereport.presentation.home.components.HomeContent
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

                is HomeUiEvent.OnFindFinancesByMonthAndYear -> {
                    viewModel.findFinancesByMonthAndYear(event.month, event.year)
                }

                is HomeUiEvent.OnUpdateFinance -> {
                    viewModel.onUpdateFinance(event.finance)
                }

                is HomeUiEvent.OnDeleteFinance -> {
                    viewModel.onDeleteFinance(event.finance)
                }
            }
        }
    }

    HomeScreen(viewModel = viewModel, uiState = viewModel.uiState)
}

@Composable
fun HomeScreen(viewModel: HomeUiAction, uiState: HomeUiState) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End)
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
                HomeContentScreen(viewModel = viewModel, uiState = uiState)
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
fun HomeContentScreen(viewModel: HomeUiAction, uiState: HomeUiState) {
    var financesList by remember { mutableStateOf<List<Finance>?>(null) }
    var financeTypeSelected by remember { mutableStateOf<FinanceTypes?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    HomeContent(financesTypes = uiState.financeTypes,
        finances = uiState.finances,
        onFinances = { finances, financeType ->
            financesList = finances
            financeTypeSelected = financeType
            showDialog = true
        })

    if (financesList == null || financeTypeSelected == null) return

    FinancesDetailDialog(isVisible = showDialog,
        financesList = financesList!!,
        financeType = financeTypeSelected!!,
        categories = uiState.categories,
        financeTypes = uiState.financeTypes,
        onDismiss = {
            financesList = null
            showDialog = false
        },
        onUpdateFinance = { finance ->
            financesList = null
            showDialog = false

            if (finance == null) return@FinancesDetailDialog
            viewModel.onUpdateFinance(finance = finance)
        },
        onDeleteFinance = { finance ->
            financesList = null
            showDialog = false

            if (finance == null) return@FinancesDetailDialog
            viewModel.onDeleteFinance(finance = finance)
        })
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

@Preview(name = "Finances Empty Home Screen")
@Composable
fun HomeScreenFinancesEmptyPreview() {
    HomeScreen(
        viewModel = HomeUiAction.buildFake(), uiState = MutableHomeUiState.buildEmptyFinancesFake()
    )
}
