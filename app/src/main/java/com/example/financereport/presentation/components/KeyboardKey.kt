package com.example.financereport.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financereport.ui.theme.Gray50


@Composable
fun KeyboardKey(
    label: String, color: Color = Gray50, textColor: Color = Color.Black, onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(color)
            .clickable { onClick() }, contentAlignment = Alignment.Center
    ) {
        Text(text = label, fontSize = 24.sp, color = textColor)
    }
}

@Preview
@Composable
fun KeyboardKeyPreview() {
    KeyboardKey(label = "1", onClick = {})
}