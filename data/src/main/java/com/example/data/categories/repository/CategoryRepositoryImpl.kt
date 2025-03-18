package com.example.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.example.data.R
import com.example.data.categories.mapper.toDomain
import com.example.data.categories.mapper.toEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.categories.repository.CategoryRepository
import com.example.domain.utils.ColorUtils.getColorHex
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val context: Context,
    private val financeTypesLocalDataSource: FinanceTypesLocalDataSource,
    private val localDataSource: CategoryLocalDataSource,
) : CategoryRepository {

    private var incomeId = 1
    private var expenseId = 2
    private var savingId = 3

    override suspend fun getCategoryList(): List<Category> {
        val financeTypes = financeTypesLocalDataSource.getAll().getOrThrow().map { it.toDomain() }
        localDataSource.insertOrUpdate(createCategoryList(financeTypes = financeTypes).map { it.toEntity() })
        return localDataSource.getAll().getOrThrow()
            .map { it.toDomain(financeTypes.find { financeType -> financeType.id == it.financeTypeId }!!) }
    }

    private fun createCategoryList(financeTypes: List<FinanceTypes>): List<Category> {
        return getStaticCategories(resources = context.resources, financeTypes = financeTypes)
    }

    private fun getStaticCategories(
        resources: Resources, financeTypes: List<FinanceTypes>
    ): List<Category> {
        val categories = mutableListOf<Category>()
        val income = financeTypes.find { it.id == incomeId }
        val expense = financeTypes.find { it.id == expenseId }
        val saving = financeTypes.find { it.id == savingId }

        if (income == null || expense == null || saving == null) {
            throw IllegalStateException("Finance types not found")
        }

        categories.add(
            Category(
                1,
                R.drawable.income,
                resources.getString(R.string.payroll),
                getColorHex(context, R.color.green),
                income
            )
        )
        categories.add(
            Category(
                2,
                R.drawable.transfer,
                resources.getString(R.string.transfer),
                getColorHex(context, R.color.blue),
                income
            )
        )
        categories.add(
            Category(
                3,
                R.drawable.rents,
                resources.getString(R.string.rents),
                getColorHex(context, R.color.mint),
                income
            )
        )
        categories.add(
            Category(
                4,
                R.drawable.income,
                resources.getString(R.string.scholarships),
                getColorHex(context, R.color.coral),
                income
            )
        )
        categories.add(
            Category(
                5,
                R.drawable.transfer,
                resources.getString(R.string.extraordinary_income),
                getColorHex(context, R.color.green),
                income
            )
        )
        categories.add(
            Category(
                6,
                R.drawable.income,
                resources.getString(R.string.equity_income),
                getColorHex(context, R.color.blue),
                income
            )
        )

        // Savings
        categories.add(
            Category(
                7,
                R.drawable.cryptocurrencies,
                resources.getString(R.string.cryptocurrencies),
                getColorHex(context, R.color.coral),
                saving
            )
        )
        categories.add(
            Category(
                8,
                R.drawable.stock_exchange,
                resources.getString(R.string.stock_exchange),
                getColorHex(context, R.color.sky_blue),
                saving
            )
        )
        categories.add(
            Category(
                9,
                R.drawable.savings,
                resources.getString(R.string.savings),
                getColorHex(context, R.color.green),
                saving
            )
        )
        return categories
    }
}