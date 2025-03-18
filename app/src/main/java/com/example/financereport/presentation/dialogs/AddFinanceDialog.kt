package com.example.financereport.presentation.dialogs

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.presentation.components.AmountInputField
import com.example.financereport.presentation.components.DatePickerModal
import com.example.financereport.presentation.components.KeyboardKey
import com.example.financereport.presentation.components.NumericKeyboard
import com.example.financereport.presentation.components.PillDropdown
import com.example.financereport.ui.theme.Blue30
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFinanceDialog(
    isVisible: Boolean,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    onDismiss: (Finance?) -> Unit
) {
    if (isVisible) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        LaunchedEffect(Unit) { sheetState.expand() }

        val amount = remember { mutableStateOf("0.00") }

        ModalBottomSheet(
            onDismissRequest = { onDismiss(null) },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            AddFinanceDialogContent(
                amount = amount,
                categories = categories,
                financeTypes = financeTypes,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun AddFinanceDialogContent(
    amount: MutableState<String>,
    categories: List<Category>,
    financeTypes: List<FinanceTypes>,
    onDismiss: (Finance?) -> Unit
) {
    val financeType = remember { mutableStateOf(financeTypes.first()) }
    val category =
        remember { mutableStateOf(categories.first { it.financeType.id == financeType.value.id }) }
    val showDatePicker = remember { mutableStateOf(false) }
    val dateLong = remember { mutableLongStateOf(System.currentTimeMillis()) }

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
        Text(
            text = category.value.name, fontSize = 16.sp, color = Color.Gray
        )
        AmountInputField(value = amount.value)
        Text(
            text = "${Date(dateLong.longValue)}", fontSize = 16.sp, color = Color.Gray
        )
        TextField(value = "",
            onValueChange = {},
            placeholder = { Text("Add comment...") },
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
            when (key) {
                "⌫" -> if (amount.value.isNotEmpty()) {
                    amount.value = amount.value.dropLast(1)
                }

                "." -> if (!amount.value.contains(".")) {
                    amount.value += key
                }

                else -> {
                    if (amount.value == "0.00") {
                        amount.value = key
                    } else {
                        amount.value += key
                    }
                }
            }

            if (amount.value.isEmpty() || amount.value.equals("0.00")) amount.value = "0.00"
        })

        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            KeyboardKey(label = "📅", color = Blue30, onClick = { showDatePicker.value = true })
            KeyboardKey(label = "✔", color = Color.Black, textColor = Color.White, onClick = {
                if (!validateValues(amount.value, category.value)) {
                    Log.i("AddFinanceDialog", "Invalid values")
                } else {
                    onDismiss(generateFinance(amount.value, category.value, dateLong.longValue))
                }
            })
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showDatePicker.value) {
        DatePickerModal(onDateSelected = { it?.let { dateLong.longValue = it } },
            onDismiss = { showDatePicker.value = false },
            initialDateMillis = dateLong.longValue
        )
    }
}

fun validateValues(amount: String, category: Category?): Boolean {
    return amount.isNotEmpty() && category != null
}

fun generateFinance(amount: String, category: Category, date: Long): Finance? {
    try {
        return Finance(
            amount = amount.toDouble(),
            category = category,
            date = Date(date),
        )
    } catch (e: Exception) {
        Log.e("AddFinanceDialog", "Error generating finance: ${e.message}")
        return null
    }
}

@Preview(showBackground = true)
@Composable
fun AddFinanceDialogPreview() {
    val amount = remember { mutableStateOf("0.00") }
    val financeType1 = FinanceTypes.buildFake()
    val financeType2 = FinanceTypes.buildFake()

    val categories = listOf(
        Category.buildFakeByFinanceType(financeType = financeType1),
        Category.buildFakeByFinanceType(financeType = financeType1),
        Category.buildFakeByFinanceType(financeType = financeType2),
        Category.buildFakeByFinanceType(financeType = financeType2),
        Category.buildFakeByFinanceType(financeType = financeType2)
    )
    val financeTypes = listOf(financeType1, financeType2)
    AddFinanceDialogContent(amount = amount,
        categories = categories,
        financeTypes = financeTypes,
        onDismiss = { _ -> })
}