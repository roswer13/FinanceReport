package com.roswer.financereport.presentation.dialogs

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.module.categories.model.FinanceTypes
import com.roswer.domain.module.finances.models.Finance
import com.roswer.financereport.R
import com.roswer.financereport.constants.FirebaseConstants
import com.roswer.financereport.presentation.components.AlertInformationDialog
import com.roswer.financereport.presentation.components.CircleIcon
import com.roswer.financereport.presentation.components.TextFontWeight
import com.roswer.financereport.utils.FirebaseAnalyticsUtil
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
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

@OptIn(ExperimentalFoundationApi::class)
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
    var selectedFinance by remember { mutableStateOf<Finance?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }

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
            style = MaterialTheme.typography.titleLarge
        )
        if (financesList.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp, top = 6.dp)
                    .fillMaxWidth(),
                text = "No finances found",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            val financesGroupDate = financesToFinancesGroupDate(financesList)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                financesGroupDate.forEach { (date, financesItems) ->
                    stickyHeader {
                        Surface(color = MaterialTheme.colorScheme.background) {
                            Text(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                text = date,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }

                    items(financesItems) { finance ->
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
                                emojiIcon = finance.category.emojiIcon
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
                                TextFontWeight(
                                    modifier = Modifier.fillMaxHeight(),
                                    text = currencyInstance.format(finance.amount),
                                    fontWeight = FontWeight.Bold
                                )
                                Icon(
                                    modifier = Modifier
                                        .padding(start = 12.dp)
                                        .clickable {
                                            selectedFinance = finance
                                            showDeleteConfirmation = true
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

    if (showDeleteConfirmation){
        AlertInformationDialog(
            title = "${stringResource(R.string.delete)}: ${selectedFinance?.category?.name}",
            message = stringResource(R.string.delete_confirmation),
            onDismiss = { showDeleteConfirmation = false },
            onConfirm = {
                onDeleteFinance(selectedFinance)
                showDeleteConfirmation = false
            }
        )
    }
}

/**
 * Group finances by date and format the date to "EEEE, d".
 */
fun financesToFinancesGroupDate(finances: List<Finance>): Map<String, List<Finance>> {
    val dateFormatter = SimpleDateFormat("EEEE, d", Locale.getDefault())
    return finances.groupBy { dateFormatter.format(it.date) }
}

@Preview(
    showBackground = true, name = "Finances Detail Content"
)
@Composable
fun FinancesDetailContentPreview() {
    val yesterdayDate = Date(System.currentTimeMillis() - 86400000 * 2)
    val financeType = FinanceTypes.buildIncomeFake()
    val financesList = listOf(
        Finance.buildIncomeFake(),
        Finance.buildSavingFake(),
        Finance.buildExpenseFake(),
        // Finance before yesterday
        Finance.buildIncomeByDateFake(date = yesterdayDate),
        Finance.buildIncomeByDateFake(date = yesterdayDate),
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