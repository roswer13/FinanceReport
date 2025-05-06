package com.roswer.domain.module.finances.models

import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.utils.generateRandomParagraph
import java.util.Date
import kotlin.random.Random

class Finance(
    var id: Int = 0,
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
            description = generateRandomParagraph(wordCount = 8, allowEmpty = true),
            creationDate = Date()
        )

        fun buildIncomeByDateFake(date: Date) = Finance(
            id = 1,
            date = date,
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildIncomeFake(),
            description = generateRandomParagraph(wordCount = 8, allowEmpty = true),
            creationDate = date
        )

        fun buildSavingFake() = Finance(
            id = 1,
            date = Date(),
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildSavingFake(),
            description = generateRandomParagraph(wordCount = 8, allowEmpty = true),
            creationDate = Date()
        )

        fun buildExpenseFake() = Finance(
            id = 1,
            date = Date(),
            amount = Random.nextDouble(10.0, 100.0),
            category = Category.buildExpenseFake(),
            description = generateRandomParagraph(wordCount = 8, allowEmpty = true),
            creationDate = Date()
        )
    }
}