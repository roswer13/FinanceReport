package com.example.financereport.presentation.home.viewmodel

import com.example.domain.module.finances.models.Finance

sealed interface HomeUiEvent {
    data class OnCreateFinance(val finance: Finance) : HomeUiEvent
}