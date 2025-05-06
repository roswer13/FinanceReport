package com.roswer.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.roswer.data.R
import com.roswer.data.categories.mapper.toDomain
import com.roswer.data.categories.mapper.toEntity
import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.module.categories.model.FinanceTypes
import com.roswer.domain.module.categories.repository.CategoryRepository
import com.roswer.domain.utils.ColorUtils.getColorHex
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
                "💵",
                resources.getString(R.string.payroll),
                getColorHex(context, R.color.green),
                income
            )
        )
        categories.add(
            Category(
                2,
                R.drawable.transfer,
                "💸",
                resources.getString(R.string.transfer),
                getColorHex(context, R.color.blue),
                income
            )
        )
        categories.add(
            Category(
                3,
                R.drawable.rents,
                "🏠",
                resources.getString(R.string.rents),
                getColorHex(context, R.color.green),
                income
            )
        )
        categories.add(
            Category(
                4,
                R.drawable.income,
                "🎓",
                resources.getString(R.string.scholarships),
                getColorHex(context, R.color.orange),
                income
            )
        )
        categories.add(
            Category(
                5,
                R.drawable.transfer,
                "💰",
                resources.getString(R.string.extraordinary_income),
                getColorHex(context, R.color.green),
                income
            )
        )
        categories.add(
            Category(
                6,
                R.drawable.income,
                "📈",
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
                "💰",
                resources.getString(R.string.cryptocurrencies),
                getColorHex(context, R.color.orange),
                saving
            )
        )
        categories.add(
            Category(
                8,
                R.drawable.stock_exchange,
                "📈",
                resources.getString(R.string.stock_exchange),
                getColorHex(context, R.color.sky_blue),
                saving
            )
        )
        categories.add(
            Category(
                9,
                R.drawable.savings,
                "🏦",
                resources.getString(R.string.savings),
                getColorHex(context, R.color.green),
                saving
            )
        )

        // Expenses
        categories.add(
            Category(
                10,
                R.drawable.building,
                "🏠",
                resources.getString(R.string.rent_or_mortgage),
                getColorHex(context, R.color.green),
                expense
            )
        )
        categories.add(
            Category(
                11,
                R.drawable.burger,
                "🍔",
                resources.getString(R.string.food),
                getColorHex(context, R.color.blue),
                expense
            )
        )
        categories.add(
            Category(
                12,
                R.drawable.shopping_bag,
                "🛒",
                resources.getString(R.string.shopping),
                getColorHex(context, R.color.lavender),
                expense
            )
        )
        categories.add(
            Category(
                13,
                R.drawable.devices,
                "📱",
                resources.getString(R.string.phone_internet),
                getColorHex(context, R.color.orange),
                expense
            )
        )
        categories.add(
            Category(
                14,
                R.drawable.bolt,
                "💡",
                resources.getString(R.string.electricity),
                getColorHex(context, R.color.yellow),
                expense
            )
        )
        categories.add(
            Category(
                15,
                R.drawable.flame,
                "⛽",
                resources.getString(R.string.gas),
                getColorHex(context, R.color.pink),
                expense
            )
        )
        categories.add(
            Category(
                16,
                R.drawable.droplet,
                "💧",
                resources.getString(R.string.water),
                getColorHex(context, R.color.sky_blue),
                expense
            )
        )
        categories.add(
            Category(
                17,
                R.drawable.car,
                "🚗",
                resources.getString(R.string.transport_fuel),
                getColorHex(context, R.color.green),
                expense
            )
        )
        categories.add(
            Category(
                18,
                R.drawable.file_text_shield,
                "🛡️",
                resources.getString(R.string.insurance),
                getColorHex(context, R.color.red),
                expense
            )
        )
        categories.add(
            Category(
                19,
                R.drawable.receipt,
                "💰",
                resources.getString(R.string.taxes_fines),
                getColorHex(context, R.color.green),
                expense
            )
        )
        categories.add(
            Category(
                20,
                R.drawable.brand_feedly,
                "📚",
                resources.getString(R.string.fees_dues),
                getColorHex(context, R.color.blue),
                expense
            )
        )
        categories.add(
            Category(
                21,
                R.drawable.sparkles,
                "🎉",
                resources.getString(R.string.leisure),
                getColorHex(context, R.color.peach),
                expense
            )
        )
        categories.add(
            Category(
                22,
                R.drawable.beach,
                "✈️",
                resources.getString(R.string.travel),
                getColorHex(context, R.color.red),
                expense
            )
        )
        categories.add(
            Category(
                23,
                R.drawable.receipt,
                "💳",
                resources.getString(R.string.extra_expenses),
                getColorHex(context, R.color.lavender),
                expense
            )
        )
        categories.add(
            Category(
                24,
                R.drawable.receipt,
                "💸",
                resources.getString(R.string.asset_losses),
                getColorHex(context, R.color.blue),
                expense
            )
        )
        categories.add(
            Category(
                25,
                R.drawable.receipt,
                "💳",
                resources.getString(R.string.others),
                getColorHex(context, R.color.peach),
                expense
            )
        )
        return categories
    }
}