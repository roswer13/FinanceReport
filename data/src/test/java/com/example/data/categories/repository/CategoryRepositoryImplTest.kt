package com.example.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.entity.CategoryEntity
import com.example.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CategoryRepositoryImplTest {
    private lateinit var categoryRepository: CategoryRepositoryImpl
    private lateinit var localDataSource: CategoryLocalDataSource
    private lateinit var categoryDao: CategoryDao
    private lateinit var logger: Logger
    private lateinit var context: Context

    @Before
    fun setUp() {
        categoryDao = mockk()
        logger = mockk(relaxed = true)
        context = mockk {
            val resourcesMock = mockk<Resources> {
                every { getString(any()) } returns "Mocked String"
                every { getColor(any()) } returns 0xFFFFFF
            }
            every { resources } returns resourcesMock
        }
        localDataSource = CategoryLocalDataSource(categoryDao, logger)
        categoryRepository = CategoryRepositoryImpl(context, localDataSource)
    }

    @Test
    fun `getCategoryList should return list of categories`() = runBlocking {
        coEvery { localDataSource.getAll() } returns Result.success(listOf(category1, category2))
        coEvery { localDataSource.insertOrUpdate(any()) } returns Result.success(Unit)

        val result = categoryRepository.getCategoryList()

        coVerify { localDataSource.insertOrUpdate(any()) }
        coVerify { localDataSource.getAll() }
        assert(result.size == 2)
        assert(result[0].id == 1)
        assert(result[1].id == 2)
    }

    @Test
    fun `getCategoryList should return empty list when no categories`() = runBlocking {
        coEvery { localDataSource.getAll() } returns Result.success(emptyList())
        coEvery { localDataSource.insertOrUpdate(any()) } returns Result.success(Unit)

        val result = categoryRepository.getCategoryList()

        coVerify { localDataSource.insertOrUpdate(any()) }
        coVerify { localDataSource.getAll() }
        assert(result.isEmpty())
    }

    companion object {
        val category1 = CategoryEntity(
            id = 1, icon = 1, name = "Category 1", color = "#FFFFFF"
        )
        val category2 = CategoryEntity(
            id = 2, icon = 1, name = "Category 2", color = "#FFFFFF"
        )
    }
}