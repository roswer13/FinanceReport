package com.example.financereport.presentation.home.viewmodel

import com.example.domain.module.finances.models.Finance

interface HomeUiAction {
    fun onCreateFinance(finance: Finance)
    fun onUpdateFinance(finance: Finance)
    fun onDeleteFinance(finance: Finance)
    fun findFinancesByMonthAndYear(month: Int, year: Int)

    companion object {
        fun buildFake() = object : HomeUiAction {
            override fun onCreateFinance(finance: Finance) {}
            override fun onUpdateFinance(finance: Finance) {}
            override fun onDeleteFinance(finance: Finance) {}
            override fun findFinancesByMonthAndYear(month: Int, year: Int) {}
        }
    }
}