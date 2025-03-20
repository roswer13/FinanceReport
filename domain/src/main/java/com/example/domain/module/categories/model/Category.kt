package com.example.domain.module.categories.model

import com.example.domain.R
import com.example.domain.utils.ColorUtils

data class Category(
    val id: Int = 0,
    val icon: Int,
    val name: String = "",
    val color: String = "#000000",
    val financeType: FinanceTypes
) {
    companion object {
        fun buildIncomeFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            name = "Income",
            color = ColorUtils.generateRandomColorHex(),
            financeType = FinanceTypes.buildIncomeFake()
        )

        fun buildSavingFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            name = "Savings",
            color = ColorUtils.generateRandomColorHex(),
            financeType = FinanceTypes.buildSavingFake()
        )

        fun buildFakeByFinanceType(financeType: FinanceTypes) = Category(
            id = 1,
            icon = R.drawable.icon_test,
            name = "Supermarket",
            color = ColorUtils.generateRandomColorHex(),
            financeType = financeType
        )
    }
}