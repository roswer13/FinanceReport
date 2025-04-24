package com.roswer.financereport.presentation.home.viewmodel

import com.roswer.domain.module.finances.models.Finance

sealed interface HomeUiEvent {
    data class OnCreateFinance(val finance: Finance) : HomeUiEvent
    data class OnUpdateFinance(val finance: Finance) : HomeUiEvent
    data class OnDeleteFinance(val finance: Finance) : HomeUiEvent
    data class OnFindFinancesByMonthAndYear(val month: Int, val year: Int) : HomeUiEvent
}