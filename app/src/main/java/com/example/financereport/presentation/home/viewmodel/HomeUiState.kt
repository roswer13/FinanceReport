package com.example.financereport.presentation.home.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.utils.Updatable

@Stable
interface HomeUiState {
    val categories: List<Category>
    val financeTypes: List<FinanceTypes>
    val finances: List<Finance>
    val error: Boolean
}

class MutableHomeUiState : HomeUiState, Updatable {
    override var categories: List<Category> by mutableStateOf(emptyList())
    override var financeTypes: List<FinanceTypes> by mutableStateOf(emptyList())
    override var finances: List<Finance> by mutableStateOf(emptyList())
    override var error: Boolean by mutableStateOf(false)

    companion object {
        fun buildFake() = MutableHomeUiState().apply {
            categories = listOf(
                Category.buildFake(),
                Category.buildFake(),
                Category.buildFake(),
            )
            financeTypes = listOf(
                FinanceTypes.buildFake(),
                FinanceTypes.buildFake(),
                FinanceTypes.buildFake(),
            )
            finances = listOf(
                Finance.buildFake(),
                Finance.buildFake(),
                Finance.buildFake(),
                Finance.buildFake(),
                Finance.buildFake(),
            )
            error = false
        }

        fun buildEmptyFake() = MutableHomeUiState().apply {
            categories = listOf()
            financeTypes = listOf()
            finances = listOf()
            error = false
        }
    }
}