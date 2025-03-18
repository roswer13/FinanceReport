package com.example.domain.module.categories.usecase

import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.categories.repository.FinanceTypesRepository
import javax.inject.Inject

class FinanceTypesUseCase @Inject constructor(
    private val repository: FinanceTypesRepository
) {
    /**
     * Get the list of finance types.
     */
    suspend fun getFinancesTypes(): List<FinanceTypes> = repository.getFinanceTypes()
}