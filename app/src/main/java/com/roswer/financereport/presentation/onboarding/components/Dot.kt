package com.roswer.financereport.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Dot(isSelected: Boolean) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .padding(4.dp)
            .width(if (isSelected) 18.dp else 8.dp)
            .height(if (isSelected) 8.dp else 8.dp)
            .border(
                width = 1.dp, color = colorScheme.outline, shape = RoundedCornerShape(10.dp)
            )
            .background(
                color = if (isSelected) colorScheme.primary else Color(0xFFFFFFFF),
                shape = CircleShape
            )
    )
}

@Preview
@Composable
fun DotPreview() {
    val options = listOf(true, false, false)

    Row {
        options.forEach { isSelected ->
            Dot(isSelected = isSelected)
        }
    }
}