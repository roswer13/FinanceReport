package com.example.financereport.presentation.home.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.finances.models.Finance
import com.example.financereport.utils.Updatable
import java.util.Calendar

@Stable
interface HomeUiState {
    val categories: List<Category>
    val financeTypes: List<FinanceTypes>
    val finances: List<Finance>
    val error: Boolean
    val month: Int
    val year: Int
}

class MutableHomeUiState : HomeUiState, Updatable {
    override var categories: List<Category> by mutableStateOf(emptyList())
    override var financeTypes: List<FinanceTypes> by mutableStateOf(emptyList())
    override var finances: List<Finance> by mutableStateOf(emptyList())
    override var error: Boolean by mutableStateOf(false)

    override var month: Int by mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH))
    override var year: Int by mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR))

    companion object {
        fun buildFake() = MutableHomeUiState().apply {
            categories = listOf(
                Category.buildSavingFake(),
                Category.buildIncomeFake(),
                Category.buildIncomeFake(),
            )
            financeTypes = listOf(
                FinanceTypes.buildIncomeFake(),
                FinanceTypes.buildIncomeFake(),
            )
            finances = listOf(
                Finance.buildIncomeFake(),
                Finance.buildIncomeFake(),
                Finance.buildIncomeFake(),
                Finance.buildSavingFake(),
                Finance.buildSavingFake(),
            )
            month = 2
            year = 2025
            error = false
        }

        fun buildEmptyFake() = MutableHomeUiState().apply {
            categories = listOf()
            financeTypes = listOf()
            finances = listOf()
            month = 2
            year = 2025
            error = false
        }

        fun buildEmptyFinancesFake() = MutableHomeUiState().apply {
            categories = listOf(
                Category.buildSavingFake(),
                Category.buildIncomeFake(),
                Category.buildIncomeFake(),
            )
            financeTypes = listOf(
                FinanceTypes.buildIncomeFake(),
                FinanceTypes.buildIncomeFake(),
            )
            finances = listOf()
            month = 2
            year = 2025
            error = false
        }

    }
}