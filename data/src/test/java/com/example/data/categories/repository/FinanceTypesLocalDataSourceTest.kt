package com.example.data.categories.repository

import com.example.data.databese.dao.FinanceTypeDao
import com.example.data.databese.entity.FinanceTypeEntity
import com.example.domain.utils.Logger
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
            FinanceTypeEntity(id = 1, name = "Income"), FinanceTypeEntity(id = 2, name = "Expense")
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
            logger.logError(
                any(),
                match { it.contains("Failure to getAll on FinanceTypesLocalDataSource") })
        }
    }

    @Test
    fun `insertOrUpdate should return success when operation is successful`() = runBlocking {
        val financeTypes = listOf(FinanceTypeEntity(id = 1, name = "Income"))
        coEvery { financeTypeDao.insertOrUpdate(financeTypes) } returns Unit

        val result = localDataSource.insertOrUpdate(financeTypes)

        assert(result.isSuccess)
    }

    @Test
    fun `insertOrUpdate should log error and return failure when exception occurs`() = runBlocking {
        val financeTypes = listOf(FinanceTypeEntity(id = 1, name = "Income"))
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