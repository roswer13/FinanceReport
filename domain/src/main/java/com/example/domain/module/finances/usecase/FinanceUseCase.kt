package com.example.domain.module.finances.usecase

import com.example.domain.module.finances.models.Finance
import com.example.domain.module.finances.repository.FinanceRepository
import javax.inject.Inject

class FinanceUseCase @Inject constructor(
    private val repository: FinanceRepository
) {
    suspend fun getFinancesList(): List<Finance> = repository.getFinancesList()

    suspend fun getFinanceById(id: Int): Finance = repository.getFinanceById(id)

    suspend fun saveFinance(finance: Finance): Boolean = repository.saveFinance(finance)

    suspend fun deleteFinance(finance: Finance): Boolean = repository.deleteFinance(finance)
}