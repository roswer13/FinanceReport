package com.roswer.domain.module.categories.model

import com.roswer.domain.R
import com.roswer.domain.utils.ColorUtils

data class FinanceTypes(
    val id: Int = 0,
    val name: String,
    val icon: Int,
    val color: String = "#000000",
    val type: FinanceTypesEnum
) {
    companion object {
        fun buildIncomeFake() = FinanceTypes(
            id = 1,
            name = "Income",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex(),
            type = FinanceTypesEnum.INCOME
        )
        fun buildSavingFake() = FinanceTypes(
            id = 2,
            name = "Saving",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex(),
            type = FinanceTypesEnum.SAVING
        )
        fun buildExpenseFake() = FinanceTypes(
            id = 2,
            name = "Expense",
            icon = R.drawable.icon_test,
            color = ColorUtils.generateRandomColorHex(),
            type = FinanceTypesEnum.EXPENSE
        )
    }
}