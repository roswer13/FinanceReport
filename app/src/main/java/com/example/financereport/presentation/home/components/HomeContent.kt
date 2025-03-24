package com.example.financereport.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.categories.model.FinanceTypesEnum
import com.example.domain.module.finances.models.Finance
import com.example.financereport.R
import com.example.financereport.presentation.components.AnimatedCircle
import com.example.financereport.presentation.components.AnimatedCounter

@Composable
fun HomeContent(financesTypes: List<FinanceTypes>, finances: List<Finance>) {
    if (financesTypes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(text = "No finances found")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val totalAmount = finances.sumOf { it.amount }
            val financesIncome =
                finances.filter { it.category.financeType.type == FinanceTypesEnum.INCOME }
                    .sumOf { it.amount }
            val financesExpense =
                finances.filter { it.category.financeType.type == FinanceTypesEnum.EXPENSE }
                    .sumOf { it.amount }
            val financesSaving =
                finances.filter { it.category.financeType.type == FinanceTypesEnum.SAVING }
                    .sumOf { it.amount }

            val totalAmountFinance = financesIncome - financesExpense
            val totalAmountReal = totalAmountFinance - financesSaving

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = stringResource(R.string.saving), fontSize = 18.sp)
                AnimatedCounter(
                    targetNumber = totalAmountFinance, bigNumber = true
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = stringResource(R.string.available_saving))
                AnimatedCounter(targetNumber = totalAmountReal)
            }

            val financesTypesColors = financesTypes.map { Color(it.color.toColorInt()) }
            val financesTotalsByFinancesType = financesTypes.map { financeType ->
                (finances.filter { it.category.financeType.id == financeType.id }
                    .sumOf { it.amount } / totalAmount).toFloat()
            }

            AnimatedCircle(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .weight(1.5f),
                proportions = financesTotalsByFinancesType,
                colors = financesTypesColors
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            ) {
                items(financesTypes.size) { position ->
                    val financeType = financesTypes[position]
                    val total = finances.filter { it.category.financeType.id == financeType.id }
                        .sumOf { it.amount }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(70.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = Color(financeType.color.toColorInt()),
                                        shape = CircleShape
                                    )
                            )

                            Icon(
                                painter = painterResource(id = financeType.icon),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = Color.DarkGray
                            )
                        }
                        Column {
                            Text(text = financeType.name)
                            AnimatedCounter(targetNumber = total)
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    val financeTypes = listOf(
        FinanceTypes.buildIncomeFake(),
        FinanceTypes.buildExpenseFake(),
        FinanceTypes.buildSavingFake(),
    )
    val finances = listOf(
        Finance.buildIncomeFake(),
        Finance.buildIncomeFake(),
        Finance.buildIncomeFake(),
        Finance.buildSavingFake(),
        Finance.buildSavingFake(),
        Finance.buildExpenseFake(),
        Finance.buildExpenseFake(),
    )
    HomeContent(financesTypes = financeTypes, finances = finances)
}