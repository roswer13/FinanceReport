package com.example.domain.module.finances.models

import com.example.domain.module.categories.model.Category
import java.util.Date
import kotlin.random.Random

class Finance(
    val id: Int = 0,
    val date: Date,
    val amount: Double,
    val category: Category,
    val description: String? = "",
    val creationDate: Date = Date()
) {
    companion object {
        fun buildIncomeFake() = Finance(
            id = 1,
            date = Date(),
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildIncomeFake(),
            description = "Description of the finance",
            creationDate = Date()
        )

        fun buildSavingFake() = Finance(
            id = 1,
            date = Date(),
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildSavingFake(),
            description = "Description of the finance",
            creationDate = Date()
        )

        fun buildExpenseFake() = Finance(
            id = 1,
            date = Date(),
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildExpenseFake(),
            description = "Description of the expense",
            creationDate = Date()
        )
    }
}