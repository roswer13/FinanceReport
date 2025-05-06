package com.roswer.data.categories.repository

import com.roswer.data.databese.dao.FinanceTypeDao
import com.roswer.data.databese.entity.FinanceTypeEntity
import com.roswer.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FinanceTypesLocalDataSourceTest {
    private lateinit var localDataSource: FinanceTypesLocalDataSource
    private lateinit var financeTypeDao: FinanceTypeDao
    private lateinit var logger: Logger

    @Before
    fun setUp() {
        financeTypeDao = mockk()
        logger = mockk(relaxed = true)
        localDataSource = FinanceTypesLocalDataSource(financeTypeDao, logger)
    }

    @Test
    fun `getAll should return list of finance types`() = runBlocking {
        val financeTypes = listOf(
            FinanceTypeEntity(
                id = 1,
                name = "Income",
                icon = 1,
                color = "#FFFFFF",
                emojiIcon = "⚗️",
                type = 1
            ),
            FinanceTypeEntity(
                id = 2,
                name = "Expense",
                icon = 2,
                color = "#FFFFFF",
                emojiIcon = "⚗️",
                type = 2
            )
        )
        coEvery { financeTypeDao.getAll() } returns financeTypes

        val result = localDataSource.getAll()

        assert(result.isSuccess)
        assert(result.getOrNull() == financeTypes)
    }


    @Test
    fun `getAll should log error and return failure when exception occurs`() = runBlocking {
        val exception = Exception("Database error")
        coEvery { financeTypeDao.getAll() } throws exception

        val result = localDataSource.getAll()

        assert(result.isFailure)
        coVerify {
            logger.logError(any(),
                match { it.contains("Failure to getAll on FinanceTypesLocalDataSource") })
        }
    }

    @Test
    fun `insertOrUpdate should return success when operation is successful`() = runBlocking {
        val financeTypes = listOf(
            FinanceTypeEntity(
                id = 1, name = "Income", icon = 1, color = "#FFFFFF", emojiIcon = "⚗️", type = 1
            )
        )
        coEvery { financeTypeDao.insertOrUpdate(financeTypes) } returns Unit

        val result = localDataSource.insertOrUpdate(financeTypes)

        assert(result.isSuccess)
    }

    @Test
    fun `insertOrUpdate should log error and return failure when exception occurs`() = runBlocking {
        val financeTypes =
            listOf(
                FinanceTypeEntity(
                    id = 1,
                    name = "Income",
                    icon = 1,
                    color = "#FFFFFF",
                    emojiIcon = "⚗️",
                    type = 1
                )
            )
        val exception = Exception("Database error")
        coEvery { financeTypeDao.insertOrUpdate(financeTypes) } throws exception

        val result = localDataSource.insertOrUpdate(financeTypes)

        assert(result.isFailure)
        coVerify {
            logger.logError(any(),
                match { it.contains("Failure to insertOrUpdate on FinanceTypesLocalDataSource") })
        }
    }
}