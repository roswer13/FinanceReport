package com.example.financereport.presentation.dialogs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.financereport.presentation.components.AmountInputField
import com.example.financereport.presentation.components.CategoryItem
import com.example.financereport.presentation.components.DatePickerModal
import com.example.financereport.presentation.components.KeyboardKey
import com.example.financereport.presentation.components.NumericKeyboard
import com.example.financereport.ui.theme.Blue30
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFinanceDialog(
    isVisible: Boolean, categories: List<Category>, onDismiss: () -> Unit
) {
    if (isVisible) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        LaunchedEffect(Unit) {
            sheetState.expand()
        }

        val amount = remember { mutableStateOf("0.00") }
        Log.i("AddFinanceDialog", "Amount: ${amount.value}")
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            AddFinanceDialogContent(amount = amount, categories = categories)
        }
    }
}

@Composable
fun AddFinanceDialogContent(amount: MutableState<String>, categories: List<Category>) {
    val category = remember { mutableStateOf<Category?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }
    val dateLong = remember { mutableLongStateOf( System.currentTimeMillis()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(categories.size) { index ->
                CategoryItem(category = categories[index], onClick = {
                    category.value = categories[index]
                })
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = category.value?.name ?: "", fontSize = 16.sp, color = Color.Gray
        )
        AmountInputField(value = amount.value)
        Text(
            text = "${Date(dateLong.longValue)}", fontSize = 16.sp, color = Color.Gray
        )
        TextField(
            value = "",
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
            KeyboardKey(label = "✔", color = Color.Black, textColor = Color.White, onClick = { })
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    if (showDatePicker.value) {
        DatePickerModal(
            onDateSelected = { it?.let { dateLong.longValue = it }},
            onDismiss = { showDatePicker.value = false },
            initialDateMillis = dateLong.longValue
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddFinanceDialogPreview() {
    val amount = remember { mutableStateOf("0.00") }
    val categories = listOf(
        Category.buildFake(),
        Category.buildFake(),
        Category.buildFake(),
        Category.buildFake(),
    )
    AddFinanceDialogContent(amount = amount, categories = categories)
}