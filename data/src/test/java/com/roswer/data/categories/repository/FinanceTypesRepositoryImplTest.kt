package com.roswer.data.categories.repository

import android.content.Context
import android.content.res.Resources
import com.roswer.data.databese.dao.FinanceTypeDao
import com.roswer.data.databese.entity.FinanceTypeEntity
import com.roswer.domain.utils.Logger
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FinanceTypesRepositoryImplTest {
    private lateinit var financeTypesRepository: FinanceTypesRepositoryImpl
    private lateinit var localDataSource: FinanceTypesLocalDataSource
    private lateinit var financeTypeDao: FinanceTypeDao
    private lateinit var logger: Logger
    private lateinit var context: Context

    @Before
    fun setUp() {
        financeTypeDao = mockk()
        logger = mockk(relaxed = true)
        context = mockk {
            val resourcesMock = mockk<Resources> {
                every { getString(any()) } returns "Mocked String"
                every { getColor(any()) } returns 0xFFFFFF
            }
            every { resources } returns resourcesMock
        }
        localDataSource = FinanceTypesLocalDataSource(financeTypeDao, logger)
        financeTypesRepository = FinanceTypesRepositoryImpl(context, localDataSource)
    }

    @Test
    fun `getFinanceTypes should return list of finance types`() = runBlocking {
        coEvery { localDataSource.getAll() } returns Result.success(
            listOf(
                financeType1, financeType2
            )
        )
        coEvery { localDataSource.insertOrUpdate(any()) } returns Result.success(Unit)

        val result = financeTypesRepository.getFinanceTypes()

        coVerify { localDataSource.insertOrUpdate(any()) }
        coVerify { localDataSource.getAll() }
        assert(result.size == 2)
        assert(result[0].id == 1)
        assert(result[1].id == 2)
    }

    @Test
    fun `getFinanceTypes should return empty list when no finance types`() = runBlocking {
        coEvery { localDataSource.getAll() } returns Result.success(emptyList())
        coEvery { localDataSource.insertOrUpdate(any()) } returns Result.success(Unit)

        val result = financeTypesRepository.getFinanceTypes()

        coVerify { localDataSource.insertOrUpdate(any()) }
        coVerify { localDataSource.getAll() }
        assert(result.isEmpty())
    }

    companion object {
        val financeType1 = FinanceTypeEntity(
            id = 1, name = "Finance Type 1", icon = 1, emojiIcon = "⚗️", color = "#FFFFFF", type = 1
        )
        val financeType2 = FinanceTypeEntity(
            id = 2, name = "Finance Type 2", icon = 1, emojiIcon = "⚗️", color = "#FFFFFF", type = 2
        )
    }
}