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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financereport.presentation.components.AmountInputField
import com.example.financereport.presentation.components.NumericKeyboard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFinanceDialog(
    isVisible: Boolean, onDismiss: () -> Unit
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Cash", color = Color(0xFFDFF6FF))
                    Text(text = "Shopping", color = Color(0xFFE7F8E6))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Expenses", fontSize = 16.sp, color = Color.Gray
                )
                AmountInputField(value = amount.value)
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

                    if (amount.value.isEmpty())
                        amount.value = "0.00"
                })
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview
@Composable
fun AddFinanceDialogPreview() {
    AddFinanceDialog(isVisible = true) {}
}