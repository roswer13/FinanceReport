package com.example.financereport.presentation.home.viewmodel

import com.example.domain.module.finances.models.Finance

interface HomeUiAction {
    fun onCreateFinance(finance: Finance)

    companion object {
        fun buildFake() = object : HomeUiAction {
            override fun onCreateFinance(finance: Finance) {}
        }
    }
}