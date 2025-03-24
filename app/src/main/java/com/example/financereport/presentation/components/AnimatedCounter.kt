package com.example.financereport.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(targetNumber: Double, bigNumber: Boolean = false) {

    val currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault())

    AnimatedContent(targetState = targetNumber, transitionSpec = {
        slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
    }) { number ->
        Text(
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip,
            text = currencyInstance.format(number),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = if (bigNumber) 42.sp else 28.sp)
        )
    }
}

@Preview
@Composable
fun AnimatedCounterPreview() {
    AnimatedCounter(targetNumber = 1000.00)
}