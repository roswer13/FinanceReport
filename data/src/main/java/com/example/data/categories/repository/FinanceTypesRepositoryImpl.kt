package com.example.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.example.data.R
import com.example.data.categories.mapper.toDomain
import com.example.data.categories.mapper.toEntity
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.module.categories.repository.FinanceTypesRepository
import com.example.domain.utils.ColorUtils.getColorHex
import javax.inject.Inject

class FinanceTypesRepositoryImpl @Inject constructor(
    private val context: Context, private val localDataSource: FinanceTypesLocalDataSource
) : FinanceTypesRepository {

    private var incomeId = 1
    private var expenseId = 2
    private var savingId = 3

    override suspend fun getFinanceTypes(): List<FinanceTypes> {
        localDataSource.insertOrUpdate(getStaticFinanceTypes().map { it.toEntity() })
        return localDataSource.getAll().getOrThrow().map { it.toDomain() }
    }

    private fun getStaticFinanceTypes(): List<FinanceTypes> {
        return getStaticFinanceTypes(resources = context.resources)
    }

    private fun getStaticFinanceTypes(resources: Resources): List<FinanceTypes> {
        val finances = mutableListOf<FinanceTypes>()

        finances.add(
            FinanceTypes(
                id = incomeId,
                name = resources.getString(R.string.income),
                icon = R.drawable.income,
                color = getColorHex(context, R.color.green)
            )
        )
        finances.add(
            FinanceTypes(
                id = expenseId,
                name = resources.getString(R.string.expense),
                icon = R.drawable.expense,
                color = getColorHex(context, R.color.lavender)
            )
        )
        finances.add(
            FinanceTypes(
                id = savingId,
                name = resources.getString(R.string.saving),
                icon = R.drawable.savings,
                color = getColorHex(context, R.color.blue)
            )
        )

        return finances
    }
}