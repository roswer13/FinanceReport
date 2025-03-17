package com.example.domain.module.categories.repository

import com.example.domain.module.categories.model.FinanceTypes

interface FinanceTypesRepository {
    suspend fun getFinanceTypes(): List<FinanceTypes>
}