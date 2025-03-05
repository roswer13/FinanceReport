package com.example.data.categories.repository

import android.content.Context
import com.example.data.R
import com.example.data.categories.mapper.toDomain
import com.example.data.categories.mapper.toEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val context: Context, private val localDataSource: CategoryLocalDataSource
) : CategoryRepository {

    override suspend fun getCategoryList(): List<Category> {

        val categories = localDataSource.getAll().getOrThrow().map { it.toDomain() }
        if (categories.isEmpty()) {
            localDataSource.insert(createCategoryList().map { it.toEntity() })
        }
        return localDataSource.getAll().getOrThrow().map { it.toDomain() }
    }


    private fun createCategoryList(): List<Category> {
        val resources = context.resources
        val categories = mutableListOf<Category>()
        categories.add(
            Category(
                1, R.drawable.currency_dollar, resources.getString(R.string.finance), "#FFC107"
            )
        )
        categories.add(
            Category(
                2,
                R.drawable.currency_dollar,
                resources.getString(R.string.finance) + ".2",
                "#FFC107"
            )
        )

        return categories
    }
}