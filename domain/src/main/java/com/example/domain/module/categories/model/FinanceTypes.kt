package com.example.domain.module.categories.model

data class FinanceTypes(
    val id: Int = 0,
    val name: String
) {
    companion object {
        fun buildIncomeFake() = FinanceTypes(
            id = 1,
            name = "Income"
        )
        fun buildSavingFake() = FinanceTypes(
            id = 2,
            name = "Saving"
        )
    }
}