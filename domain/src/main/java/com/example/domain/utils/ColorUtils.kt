package com.example.domain.utils

import kotlin.random.Random

object ColorUtils {

    fun generateRandomColorHex(): String {
        val random = Random.Default
        val color = List(3) { random.nextInt(0, 256) }
        return String.format("#%02X%02X%02X", color[0], color[1], color[2])
    }
}