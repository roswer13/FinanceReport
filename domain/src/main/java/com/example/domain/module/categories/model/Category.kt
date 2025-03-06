package com.example.domain.module.categories.model

import com.example.domain.R
import com.example.domain.utils.ColorUtils

data class Category(
    val id: Int = 0, val icon: Int, val name: String = "", val color: String = "#000000"
) {
    companion object {
        fun buildFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            name = "Supermarket",
            color = ColorUtils.generateRandomColorHex()
        )
    }
}