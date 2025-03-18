package com.example.data.categories.repository

import com.example.data.databese.dao.FinanceTypeDao
import com.example.data.databese.entity.FinanceTypeEntity
import com.example.domain.utils.Logger
import javax.inject.Inject

class FinanceTypesLocalDataSource @Inject constructor(
    private val financeTypeDao: FinanceTypeDao, private val logger: Logger
) {
    private val tag = this::class.simpleName ?: "FinanceTypesLocalDataSource"

    suspend fun getAll(): Result<List<FinanceTypeEntity>> = runCatching {
        financeTypeDao.getAll()
    }.onFailure {
        logger.logError(tag, "Failure to getAll on FinanceTypesLocalDataSource. $it")
    }

    suspend fun insertOrUpdate(financeTypes: List<FinanceTypeEntity>): Result<Unit> = runCatching {
        financeTypeDao.insertOrUpdate(financeTypes)
        return Result.success(Unit)
    }.onFailure {
        logger.logError(
            tag, "Failure to insertOrUpdate on FinanceTypesLocalDataSource. $it"
        )
    }
}