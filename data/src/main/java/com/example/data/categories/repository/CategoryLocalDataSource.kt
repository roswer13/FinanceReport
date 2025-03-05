package com.example.data.categories.repository

import android.util.Log
import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.entity.CategoryEntity
import javax.inject.Inject

class CategoryLocalDataSource @Inject constructor(
    private val categoryDao: CategoryDao
) {

    suspend fun getAll(): Result<List<CategoryEntity>> = runCatching {
        categoryDao.getAll()
    }.onFailure {
        Log.e("CategoryLocalDataSource", "Error on get all categories", it)
    }

    suspend fun insert(categories: List<CategoryEntity>): Result<Unit> = runCatching {
        categoryDao.insert(categories)
        return Result.success(Unit)
    }.onFailure {
        Log.e("CategoryLocalDataSource", "Error on insert categories", it)
    }
}