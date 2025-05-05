package com.roswer.financereport.presentation.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmojiAnimated(emoji: String, currentPage: Int, page: Int) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(currentPage == page) {
        visible = currentPage == page
    }

    AnimatedVisibility(
        visible = visible,
        enter = scaleIn(tween(durationMillis = 500)) + fadeIn(tween(durationMillis = 500)),
        exit = fadeOut()
    ) {
        Text(
            text = emoji,
            fontSize = 100.sp,
            modifier = Modifier
                .padding(16.dp)
                .graphicsLayer {
                    scaleX = 1.2f
                    scaleY = 1.2f
                }
        )
    }
}