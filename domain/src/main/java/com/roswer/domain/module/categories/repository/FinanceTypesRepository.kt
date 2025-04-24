package com.roswer.domain.module.categories.repository

import com.roswer.domain.module.categories.model.FinanceTypes

interface FinanceTypesRepository {
    suspend fun getFinanceTypes(): List<FinanceTypes>
}