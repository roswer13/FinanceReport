package com.example.data.categories.repository

import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.entity.CategoryEntity
import com.example.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CategoryLocalDataSourceTest {
    private lateinit var categoryLocalDataSource: CategoryLocalDataSource
    private lateinit var categoryDao: CategoryDao
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        categoryDao = mockk()
        logger = mockk(relaxed = true)
        categoryLocalDataSource = CategoryLocalDataSource(categoryDao, logger)
    }

    @Test
    fun `should call categoryDao getAll`() = runBlocking {
        // Given
        coEvery { categoryDao.getAll() } returns listOf(category1, category2)
        every { logger.logError(any(), any()) } returns Unit

        // When
        val result = categoryLocalDataSource.getAll()

        // Then
        coVerify { categoryDao.getAll() }
        assert(result.isSuccess)
        assert(result.getOrNull()?.size == 2)
    }

    @Test
    fun `should return failure when categoryDao getAll fails`() = runBlocking {
        // Given
        coEvery { categoryDao.getAll() } throws Exception("Failed to getAll categories")

        // When
        val result = categoryLocalDataSource.getAll()

        // Then
        coVerify { categoryDao.getAll() }
        coVerify { logger.logError(any(), any()) }
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Failed to getAll categories")
    }

    @Test
    fun `should call categoryDao insert`() = runBlocking {
        // Given
        coEvery { categoryDao.insert(any<List<CategoryEntity>>()) } returns listOf(1L, 2L)
        every { logger.logError(any(), any()) } returns Unit

        // When
        val result = categoryLocalDataSource.insert(listOf(category1, category2))

        // Then
        coVerify { categoryDao.insert(listOf(category1, category2)) }
        assert(result.isSuccess)
    }

    @Test
    fun `should return failure when categoryDao insert fails`() = runBlocking {
        // Given
        coEvery { categoryDao.insert(any<List<CategoryEntity>>()) } throws Exception("Failed to insert categories")

        // When
        val result = categoryLocalDataSource.insert(listOf(category1, category2))

        // Then
        coVerify { categoryDao.insert(listOf(category1, category2)) }
        coVerify { logger.logError(any(), any()) }
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Failed to insert categories")
    }

    @Test
    fun `should call categoryDao insertOrUpdate`() = runBlocking {
        // Given
        coEvery { categoryDao.insertOrUpdate(any<List<CategoryEntity>>()) } returns Unit
        every { logger.logError(any(), any()) } returns Unit

        // When
        val result = categoryLocalDataSource.insertOrUpdate(listOf(category1, category2))

        // Then
        coVerify { categoryDao.insertOrUpdate(listOf(category1, category2)) }
        assert(result.isSuccess)
    }

    @Test
    fun `should return failure when categoryDao insertOrUpdate fails`() = runBlocking {
        // Given
        coEvery { categoryDao.insertOrUpdate(any<List<CategoryEntity>>()) } throws Exception("Failed to insertOrUpdate categories")

        // When
        val result = categoryLocalDataSource.insertOrUpdate(listOf(category1, category2))

        // Then
        coVerify { categoryDao.insertOrUpdate(listOf(category1, category2)) }
        coVerify { logger.logError(any(), any()) }
        assert(result.isFailure)
        assert(result.exceptionOrNull()?.message == "Failed to insertOrUpdate categories")
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