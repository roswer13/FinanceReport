package com.roswer.financereport.presentation.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.module.categories.model.FinanceTypes
import com.roswer.domain.module.finances.models.Finance
import com.roswer.financereport.R
import com.roswer.financereport.presentation.components.AmountInputField
import com.roswer.financereport.presentation.components.DatePickerModal
import com.roswer.financereport.presentation.components.KeyboardKey
import com.roswer.financereport.presentation.components.NumericKeyboard
import com.roswer.financereport.presentation.components.PillDropdown
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFinanceDialog(
    isVisible: Boolean,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    finance: Finance? = null,
    onDismiss: (Finance?) -> Unit
) {
    if (isVisible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        LaunchedEffect(Unit) { sheetState.expand() }

        ModalBottomSheet(
            onDismissRequest = { onDismiss(null) },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            AddFinanceDialogContent(
                categories = categories,
                financeTypes = financeTypes,
                finance = finance,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun AddFinanceDialogContent(
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    finance: Finance? = null,
    onDismiss: (Finance?) -> Unit
) {
    val amountValue = finance?.amount?.toString() ?: "0.00"
    val financeTypeValue = finance?.category?.financeType ?: financeTypes.first()
    val categoryValue = finance?.category ?: categories.first { it.financeType.id == financeTypeValue.id }
    val dateLongValue = finance?.date?.time ?: System.currentTimeMillis()
    val commentsValue = finance?.description ?: ""

    val amount = remember { mutableStateOf(amountValue) }
    val financeType = remember { mutableStateOf(financeTypeValue) }
    val category = remember { mutableStateOf(categoryValue) }
    val showDatePicker = remember { mutableStateOf(false) }
    val dateLong = remember { mutableLongStateOf(dateLongValue) }
    val comments = remember { mutableStateOf(commentsValue) }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            PillDropdown(items = financeTypes.map { it.name },
                selectedItem = financeType.value.name,


                onItemSelected = { name ->
                    financeType.value =
                        financeTypes.find { it.name == name } ?: financeTypes.first()
                    category.value = categories.first { it.financeType.id == financeType.value.id }
                })

            PillDropdown(items = categories.filter { it.financeType.id == financeType.value.id }
                .map { it.name }, selectedItem = category.value.name, onItemSelected = { name ->
                category.value = categories.find { it.name == name } ?: categories.first()
            }, icon = category.value.icon, backgroundColor = category.value.color
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AmountInputField(value = amount.value)
        Text(text = dateFormat.format(Date(dateLong.longValue)))
        TextField(
            value = comments.value,
            onValueChange = { comments.value = it },
            placeholder = { Text(text = stringResource(id = R.string.add_comment)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        NumericKeyboard(onNumberClick = { key ->
            Log.i("AddFinanceDialog", "Key: $key, amount: ${amount.value}")
            amount.value = if (amount.value == "0.00") "" else amount.value
            Log.i("AddFinanceDialog", "Key: $key, amount: ${amount.value}")
            when (key) {
                "⌫" -> if (amount.value.isNotEmpty()) {
                    amount.value = amount.value.dropLast(1)
                }

                "." -> if (!amount.value.contains(".")) {
                    amount.value += key
                }

                else -> {
                    val parts = amount.value.split(".")
                    if (parts.size == 1 || (parts.size == 2 && parts[1].length < 2)) {
                        amount.value += key
                    }
                }
            }

            Log.i("AddFinanceDialog", "Key: $key, amount: ${amount.value}")
            if (amount.value.isEmpty() || amount.value == "0.00") amount.value = "0.00"
        })

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            KeyboardKey(
                label = "📅",
                icon = R.drawable.calendar,
                onClick = { showDatePicker.value = true })
            KeyboardKey(label = "✔", icon = R.drawable.check, onClick = {
                if (!validateValues(amount.value, category.value)) {
                    Log.i("AddFinanceDialog", "Invalid values")
                } else {
                    onDismiss(
                        generateFinance(
                            finance?.id,
                            amount.value,
                            category.value,
                            dateLong.longValue,
                            comments.value
                        )
                    )
                }
            })
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showDatePicker.value) {
        DatePickerModal(
            onDateSelected = { it?.let { dateLong.longValue = it } },
            onDismiss = { showDatePicker.value = false },
            initialDateMillis = dateLong.longValue
        )
    }
}

fun validateValues(amount: String, category: Category?): Boolean {
    return amount.isNotEmpty() && category != null
}

fun generateFinance(
    id: Int?,
    amount: String,
    category: Category,
    date: Long,
    comments: String
): Finance? {
    try {
        val finance = Finance(
            amount = amount.toDouble(),
            category = category,
            date = Date(date),
            description = comments
        )
        if (id != null) finance.id = id
        return finance
    } catch (e: Exception) {
        Log.e("AddFinanceDialog", "Error generating finance: ${e.message}")
        return null
    }
}

@Preview(showBackground = true)
@Composable
fun AddFinanceDialogPreview() {
    val financeType1 = FinanceTypes.buildIncomeFake()
    val financeType2 = FinanceTypes.buildSavingFake()

    val categories = listOf(
        Category.buildFakeByFinanceType(financeType = financeType1),
        Category.buildFakeByFinanceType(financeType = financeType1),
        Category.buildFakeByFinanceType(financeType = financeType2),
        Category.buildFakeByFinanceType(financeType = financeType2),
        Category.buildFakeByFinanceType(financeType = financeType2)
    )
    val financeTypes = listOf(financeType1, financeType2)
    AddFinanceDialogContent(
        categories = categories,
        financeTypes = financeTypes,
        onDismiss = { _ -> })
}