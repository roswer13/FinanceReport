package com.roswer.domain.module.categories.usecase

import com.roswer.domain.module.categories.model.FinanceTypes
import com.roswer.domain.module.categories.repository.FinanceTypesRepository
import javax.inject.Inject

class FinanceTypesUseCase @Inject constructor(
    private val repository: FinanceTypesRepository
) {
    /**
     * Get the list of finance types.
     */
    suspend fun getFinancesTypes(): List<FinanceTypes> = repository.getFinanceTypes()
}