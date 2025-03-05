package com.example.domain.module.finances.models

import com.example.domain.module.categories.model.Category
import java.util.Date

class Finance(
    val id: Int = 0,
    val date: Date,
    val amount: Double,
    val category: Category,
    val type: FinanceTypes,
    val description: String? = "",
    val creationDate: Date
) {
    companion object {
        fun buildFake() = Finance(
            id = 1,
            date = Date(),
            amount = 100.0,
            category = Category.buildFake(),
            type = FinanceTypes.INCOME,
            description = "Description of the finance",
            creationDate = Date()
        )
    }
}