package com.example.domain.module.finances.repository

import com.example.domain.module.finances.models.Finance

interface FinanceRepository {
    suspend fun getFinancesList(): List<Finance>
    suspend fun getFinanceById(id: Int): Finance
    suspend fun saveFinance(finance: Finance): Boolean
    suspend fun deleteFinance(finance: Finance): Boolean
}