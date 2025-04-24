package com.roswer.domain.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import kotlin.random.Random

object ColorUtils {

    fun generateRandomColorHex(): String {
        val random = Random.Default
        val colors = listOf(
            Color(0xFFB388FF), // Lavender
            Color(0xFFFFC1E3), // Pink
            Color(0xFF80DEEA), // Aqua
            Color(0xFFC5E1A5)  // Light Green
        )
        val color = colors[random.nextInt(colors.size)]
        return String.format("#%06X", 0xFFFFFF and color.toArgb())
    }

    fun getColorHex(context: Context, colorResId: Int): String {
        val colorInt = ContextCompat.getColor(context, colorResId)
        return String.format("#%06X", 0xFFFFFF and colorInt)
    }
}