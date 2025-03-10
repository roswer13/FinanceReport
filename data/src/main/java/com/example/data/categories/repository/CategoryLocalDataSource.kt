package com.example.data.categories.repository

import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.entity.CategoryEntity
import com.example.domain.utils.Logger
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