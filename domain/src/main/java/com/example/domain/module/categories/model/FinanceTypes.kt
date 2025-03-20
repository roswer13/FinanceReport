package com.example.domain.module.categories.model

import com.example.domain.R
import com.example.domain.utils.ColorUtils

data class FinanceTypes(
    val id: Int = 0,
    val name: String,
    val icon: Int,
    val color: String = "#000000"
) {
    companion object {
        fun buildIncomeFake() = FinanceTypes(
            id = 1,
            name = "Income",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex()
        )
        fun buildSavingFake() = FinanceTypes(
            id = 2,
            name = "Saving",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex()
        )
        fun buildExpenseFake() = FinanceTypes(
            id = 2,
            name = "Expense",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex()
        )
    }
}