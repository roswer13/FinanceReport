package com.example.financereport.presentation.home.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.domain.module.categories.model.Category
import com.example.financereport.utils.Updatable

@Stable
interface HomeUiState {
    val categories: List<Category>
    val error: Boolean
}

class MutableHomeUiState : HomeUiState, Updatable {
    override var categories: List<Category> by mutableStateOf(emptyList())
    override var error: Boolean by mutableStateOf(false)

    companion object {
        fun buildFake() = MutableHomeUiState().apply {
            categories = listOf(
                Category.buildFake(),
                Category.buildFake(),
                Category.buildFake(),
            )
            error = false
        }

        fun buildEmptyFake() = MutableHomeUiState().apply {
            categories = listOf()
            error = false
        }
    }
}