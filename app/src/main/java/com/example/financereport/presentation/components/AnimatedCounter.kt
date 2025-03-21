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
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCounter(targetNumber: Int, bigNumber: Boolean = false) {
    AnimatedContent(targetState = targetNumber, transitionSpec = {
        slideInVertically { height -> height } + fadeIn() with slideOutVertically { height -> -height } + fadeOut()
    }) { number ->
        Text(
            text = number.toString(),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = if (bigNumber) 42.sp else 28.sp)
        )
    }
}