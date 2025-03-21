package com.example.financereport.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.presentation.components.AnimatedCircle
import com.example.financereport.presentation.components.AnimatedCounter
import com.example.financereport.ui.theme.FinanceReportTheme

@Composable
fun HomeContent(financesTypes: List<FinanceTypes>, finances : List<Finance>) {
    if (financesTypes.isEmpty()) {
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
            val totalAmount = finances.sumOf { it.amount }.toFloat()

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Total Finances", fontSize = 18.sp)
                AnimatedCounter(
                    targetNumber = totalAmount.toInt(),
                    bigNumber = true
                )
            }

            val financesTypesColors = financesTypes.map { Color(it.color.toColorInt()) }
            val financesTotalsByFinancesType = financesTypes.map { financeType -> (finances.filter {it.category.financeType.id == financeType.id}.sumOf { it.amount } / totalAmount).toFloat() }

            AnimatedCircle(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .fillMaxWidth()
                    .weight(1.5f),
                proportions = financesTotalsByFinancesType,
                colors = financesTypesColors
            )

            Row (
                modifier = Modifier.fillMaxSize().weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                financesTypes.forEach { financeType -> val total = finances.filter { it.category.financeType.id == financeType.id }.sumOf { it.amount }

                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(
                                8.dp,
                                Alignment.CenterVertically
                            )
                        ) {
                            Text(text = financeType.name, fontSize = 18.sp)
                            AnimatedCounter(targetNumber = total.toInt())
                        }
                    }
                }
            }
        }
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
fun HomeContentPreview() {
    FinanceReportTheme {
        val financeTypes = listOf(
            FinanceTypes.buildIncomeFake(),
            FinanceTypes.buildIncomeFake(),
        )
        val finances = listOf(
            Finance.buildIncomeFake(),
            Finance.buildIncomeFake(),
            Finance.buildIncomeFake(),
            Finance.buildSavingFake(),
            Finance.buildSavingFake(),
        )
        HomeContent(financesTypes = financeTypes, finances = finances)
    }
}