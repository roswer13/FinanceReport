package com.example.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.example.data.categories.mapper.toDomain
import com.example.data.categories.repository.FinanceTypesRepositoryImplTest.Companion
import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.dao.FinanceTypeDao
import com.example.data.databese.entity.CategoryEntity
import com.example.data.databese.entity.FinanceTypeEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes
import com.example.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CategoryRepositoryImplTest {
    private lateinit var repository: CategoryRepositoryImpl
    private lateinit var context: Context
    private lateinit var resources: Resources
    private lateinit var financeTypesLocalDataSource: FinanceTypesLocalDataSource
    private lateinit var categoryLocalDataSource: CategoryLocalDataSource

    @Before
    fun setUp() {
        context = mockk {
            val resourcesMock = mockk<Resources> {
                every { getString(any()) } returns "Mocked String"
                every { getColor(any()) } returns 0xFFFFFF
            }
            every { resources } returns resourcesMock
        }

        financeTypesLocalDataSource = mockk()
        categoryLocalDataSource = mockk()

        repository = CategoryRepositoryImpl(
            context = context,
            financeTypesLocalDataSource = financeTypesLocalDataSource,
            localDataSource = categoryLocalDataSource
        )
    }

    @Test
    fun `getCategoryList returns categories mapped from data source`() = runBlocking {
        // Arrange
        val incomeType = FinanceTypes(id = 1, name = "Income", icon = 123, color = "#FFFFFF")
        val expenseType = FinanceTypes(id = 2, name = "Expense", icon = 124, color = "#FFFFFF")
        val savingType = FinanceTypes(id = 3, name = "Saving", icon = 125, color = "#FFFFFF")
        val financeTypes = listOf(incomeType, expenseType, savingType)

        val financeTypeEntities = financeTypes.map {
            FinanceTypeEntity(id = it.id, name = it.name, icon = it.icon, color = it.color)
        }

        val categoryEntities = listOf(
            CategoryEntity(
                id = 1, icon = 123, name = "Category 1", color = "#FFFFFF", financeTypeId = 1
            ), CategoryEntity(
                id = 2, icon = 124, name = "Category 2", color = "#FFFFFF", financeTypeId = 2
            )
        )

        // Mock data source responses
        coEvery { financeTypesLocalDataSource.getAll() } returns Result.success(financeTypeEntities)
        coEvery { categoryLocalDataSource.insertOrUpdate(any()) } returns Result.success(Unit)
        coEvery { categoryLocalDataSource.getAll() } returns Result.success(categoryEntities)

        // Act
        val result = repository.getCategoryList()

        // Assert
        assertEquals(2, result.size)
        assertEquals("Category 1", result[0].name)
        assertEquals("Category 2", result[1].name)

        coVerify(exactly = 1) { financeTypesLocalDataSource.getAll() }
        coVerify(exactly = 1) { categoryLocalDataSource.insertOrUpdate(any()) }
        coVerify(exactly = 1) { categoryLocalDataSource.getAll() }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}