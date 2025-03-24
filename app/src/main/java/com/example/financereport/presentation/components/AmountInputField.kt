package com.example.financereport.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AmountInputField(value: String) {
    val currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault())

    Text(
        text = currencyInstance.format(value.toDouble()),
        fontSize = 32.sp,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview
@Composable
fun AmountInputFieldPreview() {
    AmountInputField(value = "1.01")
}