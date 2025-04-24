package com.roswer.data.categories.repository

import com.roswer.data.databese.dao.CategoryDao
import com.roswer.data.databese.entity.CategoryEntity
import com.roswer.domain.utils.Logger
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao, private val logger: Logger
) {

    private val tag = this::class.simpleName ?: "CategoryLocalDataSource"

    suspend fun getAll(): Result<List<CategoryEntity>> = runCatching {
        categoryDao.getAll()
    }.onFailure {
        logger.logError(tag, "Failure to getAll on CategoryLocalDataSource. $it")
    }

    suspend fun insert(categories: List<CategoryEntity>): Result<Unit> = runCatching {
        categoryDao.insert(categories)
        return Result.success(Unit)
    }.onFailure {
        logger.logError(tag, "Failure to insert on CategoryLocalDataSource. $it")
    }

    suspend fun insertOrUpdate(categories: List<CategoryEntity>): Result<Unit> = runCatching {
        categoryDao.insertOrUpdate(categories)
        return Result.success(Unit)
    }.onFailure {
        logger.logError(tag, "Failure to insertOrUpdate on CategoryLocalDataSource. $it")
    }
}