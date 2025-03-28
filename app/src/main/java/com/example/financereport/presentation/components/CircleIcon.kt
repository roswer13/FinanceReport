package com.example.financereport.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.financereport.R

@Composable
fun CircleIcon(color: Color, icon: Int) {
    Box(
        modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = color, shape = CircleShape
                )
        )
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.DarkGray
        )
    }
}

@Preview
@Composable
fun CircleIconPreview() {
    CircleIcon(
        color = Color("#FFC107".toColorInt()), icon = R.drawable.calendar
    )
}