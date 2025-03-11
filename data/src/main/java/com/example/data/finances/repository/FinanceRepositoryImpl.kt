package com.example.data.finances.repository

import com.example.data.categories.mapper.toDomain
import com.example.data.categories.repository.CategoryLocalDataSource
import com.example.data.finances.mapper.toDomain
import com.example.data.finances.mapper.toEntity
import com.example.domain.module.finances.models.Finance
import com.example.domain.module.finances.repository.FinanceRepository
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val localDataSource: FinanceLocalDataSource,
    private val categoryLocalDataSource: CategoryLocalDataSource
) : FinanceRepository {

    override suspend fun getFinancesList(): List<Finance> {
        val categories = categoryLocalDataSource.getAll().getOrThrow().map { it.toDomain() }
        return localDataSource.getAll().getOrThrow().map {
            it.toDomain(category = categories.find { category -> category.id == it.categoryId }
                ?: throw Exception("Category not found"))
        }
    }

    override suspend fun getFinanceById(id: Int): Finance {
        val categories = categoryLocalDataSource.getAll().getOrThrow().map { it.toDomain() }
        val financeEntity = localDataSource.getById(id = id).getOrThrow()
        return financeEntity.toDomain(category = categories.find { category -> category.id == financeEntity.categoryId }
            ?: throw Exception("Category not found"))
    }

    override suspend fun saveFinance(finance: Finance): Boolean {
        try {
            localDataSource.save(finance = finance.toEntity())
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteFinance(finance: Finance): Boolean {
        try {
            localDataSource.delete(finance = finance.toEntity())
            return true
        } catch (e: Exception) {
            return false
        }
    }
}