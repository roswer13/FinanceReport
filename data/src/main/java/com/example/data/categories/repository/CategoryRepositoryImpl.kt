package com.example.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.example.data.R
import com.example.data.categories.mapper.toDomain
import com.example.data.categories.mapper.toEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.repository.CategoryRepository
import com.example.domain.utils.ColorUtils.getColorHex
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val context: Context, private val localDataSource: CategoryLocalDataSource
) : CategoryRepository {

    override suspend fun getCategoryList(): List<Category> {
        // val categories = localDataSource.getAll().getOrThrow().map { it.toDomain() }
        localDataSource.insertOrUpdate(createCategoryList().map { it.toEntity() })
        return localDataSource.getAll().getOrThrow().map { it.toDomain() }
    }

    private fun createCategoryList(): List<Category> {
        return getStaticCategories(resources = context.resources)
    }
    private fun getStaticCategories(resources: Resources): List<Category> {
        val categories = mutableListOf<Category>()

        categories.add(
            Category(
                1,
                R.drawable.income,
                resources.getString(R.string.payroll),
                getColorHex(context, R.color.green)
            )
        )
        categories.add(
            Category(
                2,
                R.drawable.transfer,
                resources.getString(R.string.transfer),
                getColorHex(context, R.color.blue)
            )
        )
        categories.add(
            Category(
                3,
                R.drawable.rents,
                resources.getString(R.string.rents),
                getColorHex(context, R.color.mint)
            )
        )
        categories.add(
            Category(
                4,
                R.drawable.income,
                resources.getString(R.string.scholarships),
                getColorHex(context, R.color.coral)
            )
        )
        categories.add(
            Category(
                5,
                R.drawable.transfer,
                resources.getString(R.string.extraordinary_income),
                getColorHex(context, R.color.green)
            )
        )
        categories.add(
            Category(
                6,
                R.drawable.income,
                resources.getString(R.string.equity_income),
                getColorHex(context, R.color.blue)
            )
        )

        // Savings
        categories.add(
            Category(
                7,
                R.drawable.cryptocurrencies,
                resources.getString(R.string.cryptocurrencies),
                getColorHex(context, R.color.coral)
            )
        )
        categories.add(
            Category(
                8,
                R.drawable.stock_exchange,
                resources.getString(R.string.stock_exchange),
                getColorHex(context, R.color.sky_blue)
            )
        )
        categories.add(
            Category(
                9,
                R.drawable.savings,
                resources.getString(R.string.savings),
                getColorHex(context, R.color.green)
            )
        )
        return categories
    }
}