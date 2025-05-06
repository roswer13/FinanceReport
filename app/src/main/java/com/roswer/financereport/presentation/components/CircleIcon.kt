package com.roswer.financereport.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.roswer.financereport.R

@Composable
fun CircleIcon(color: Color, icon: Int = 0, emojiIcon: String = "") {
    Box(
        modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .background(
                    color = color, shape = CircleShape
                )
        )
        if (emojiIcon.isNotEmpty()) {
            Text(text = emojiIcon, fontSize = 18.sp)
        }
        if (icon != 0) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
fun CircleIconPreview() {
    CircleIcon(
        color = Color("#FFC107".toColorInt()), icon = R.drawable.calendar
    )
}

@Preview
@Composable
fun CircleEmojiIconPreview() {
    CircleIcon(
        color = Color("#FFC107".toColorInt()), emojiIcon = "💵"
    )
}