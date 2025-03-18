package com.example.financereport.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun AmountInputField(
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = value,
            fontSize = 32.sp,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview
@Composable
fun AmountInputFieldPreview() {
    AmountInputField(value = "1.01")
}