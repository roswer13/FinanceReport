package com.roswer.domain.module.categories.model

import com.roswer.domain.R
import com.roswer.domain.utils.ColorUtils

data class Category(
    val id: Int = 0,
    val icon: Int,
    val emojiIcon: String = "",
    val name: String = "",
    val color: String = "#000000",
    val financeType: FinanceTypes
) {
    companion object {
        fun buildIncomeFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            emojiIcon = "💵",
            name = "Income",
            color = ColorUtils.generateRandomColorHex(),
            financeType = FinanceTypes.buildIncomeFake()
        )

        fun buildSavingFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            emojiIcon = "💰",
            name = "Savings",
            color = ColorUtils.generateRandomColorHex(),
            financeType = FinanceTypes.buildSavingFake()
        )

        fun buildExpenseFake() = Category(
            id = 1,
            icon = R.drawable.icon_test,
            emojiIcon = "💸",
            name = "Expenses",
            color = ColorUtils.generateRandomColorHex(),
            financeType = FinanceTypes.buildExpenseFake()
        )

        fun buildFakeByFinanceType(financeType: FinanceTypes) = Category(
            id = 1,
            icon = R.drawable.icon_test,
            emojiIcon = "🛒",
            name = "Supermarket",
            color = ColorUtils.generateRandomColorHex(),
            financeType = financeType
        )
    }
}