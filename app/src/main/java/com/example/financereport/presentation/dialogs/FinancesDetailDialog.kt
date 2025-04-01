package com.example.financereport.presentation.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.presentation.components.CircleIcon
import com.example.financereport.presentation.components.TextFontWeight
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancesDetailDialog(
    isVisible: Boolean,
    financesList: List<Finance>,
    financeType: FinanceTypes,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    onDismiss: () -> Unit,
    onUpdateFinance: (Finance?) -> Unit,
    onDeleteFinance: (Finance?) -> Unit
) {
    if (isVisible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            FinancesDetailContent(financesList = financesList,
                financeType = financeType,
                categories = categories,
                financeTypes = financeTypes,
                onUpdateFinance = { onUpdateFinance(it) },
                onDeleteFinance = { onDeleteFinance(it) })
        }
    }
}

@Composable
fun FinancesDetailContent(
    financesList: List<Finance>,
    financeType: FinanceTypes,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    onUpdateFinance: (Finance?) -> Unit,
    onDeleteFinance: (Finance?) -> Unit
) {
    val currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault())
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var selectedFinance by remember { mutableStateOf<Finance?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .align(Alignment.CenterHorizontally),
            text = financeType.name,
            style = MaterialTheme.typography.titleMedium
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(financesList.size) { index ->
                val finance = financesList[index]

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedFinance = finance
                            showDialog = true
                        }, verticalAlignment = Alignment.CenterVertically
                ) {
                    CircleIcon(
                        color = Color(finance.category.color.toColorInt()),
                        icon = finance.category.icon
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 6.dp)
                        ) {
                            TextFontWeight(
                                text = finance.category.name, fontWeight = FontWeight.Bold
                            )
                            if (finance.description != null && finance.description!!.isNotEmpty()) {
                                Text(text = finance.description!!)
                            }
                        }
                        Column(
                            modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.End
                        ) {
                            TextFontWeight(
                                modifier = Modifier.fillMaxHeight(),
                                text = currencyInstance.format(finance.amount),
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = dateFormat.format(finance.date))
                        }
                        Icon(
                            modifier = Modifier.padding(start = 12.dp).clickable {
                                onDeleteFinance(finance)
                            },
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = Color.LightGray
                        )
                    }
                }
            }
        }
    }

    AddFinanceDialog(
        isVisible = showDialog,
        categories = categories,
        financeTypes = financeTypes,
        finance = selectedFinance
    ) { financeResult ->
        showDialog = false
        selectedFinance = null

        if (financeResult == null) return@AddFinanceDialog
        onUpdateFinance(financeResult)
    }
}

@Preview(
    showBackground = true, name = "Finances Detail Content"
)
@Composable
fun FinancesDetailContentPreview() {
    val financeType = FinanceTypes.buildIncomeFake()
    val financesList = listOf(
        Finance.buildIncomeFake(),
        Finance.buildIncomeFake(),
        Finance.buildIncomeFake(),
    )
    val categories = listOf(
        Category.buildSavingFake(),
        Category.buildIncomeFake(),
        Category.buildExpenseFake(),
    )
    val financeTypes = listOf(
        FinanceTypes.buildIncomeFake(),
        FinanceTypes.buildExpenseFake(),
        FinanceTypes.buildSavingFake(),
    )
    FinancesDetailContent(
        financesList = financesList,
        financeType = financeType,
        categories = categories,
        financeTypes = financeTypes,
        onUpdateFinance = {},
        onDeleteFinance = {}
    )
}