package com.example.domain.utils

import android.content.Context
import androidx.core.content.ContextCompat
import kotlin.random.Random

object ColorUtils {

    fun generateRandomColorHex(): String {
        val random = Random.Default
        val color = List(3) { random.nextInt(0, 256) }
        return String.format("#%02X%02X%02X", color[0], color[1], color[2])
    }

    fun getColorHex(context: Context, colorResId: Int): String {
        val colorInt = ContextCompat.getColor(context, colorResId)
        return String.format("#%06X", 0xFFFFFF and colorInt)
    }
}