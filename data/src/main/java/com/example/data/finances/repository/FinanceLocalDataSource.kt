package com.example.data.finances.repository

import com.example.data.databese.dao.FinanceDao
import com.example.data.databese.entity.FinanceEntity
import com.example.domain.utils.Logger
import java.util.Calendar
import javax.inject.Inject

class FinanceLocalDataSource @Inject constructor(
    private val financeDao: FinanceDao, private val logger: Logger
) {
    private val tag = this::class.simpleName ?: "FinanceLocalDataSource"

    suspend fun getAll(): Result<List<FinanceEntity>> = runCatching {
        financeDao.getAll()
    }.onFailure {
        logger.logError(tag, "Failure to getAll on FinanceLocalDataSource. $it")
    }

    suspend fun getById(id: Int): Result<FinanceEntity> = runCatching {
        financeDao.getById(id)
    }.onFailure {
        logger.logError(tag, "Failure to getById on FinanceLocalDataSource. $it")
    }

    suspend fun save(finance: FinanceEntity): Result<Boolean> = runCatching {
        financeDao.insert(finance)
        true
    }.onFailure {
        logger.logError(tag, "Failure to save on FinanceLocalDataSource. $it")
    }

    suspend fun delete(finance: FinanceEntity): Result<Boolean> = runCatching {
        financeDao.delete(finance)
        true
    }.onFailure {
        logger.logError(tag, "Failure to delete on FinanceLocalDataSource. $it")
    }

    suspend fun getByDay(dayTimestamp: Long): Result<List<FinanceEntity>> = runCatching {
        val startOfDay = getStartOfDay(dayTimestamp)
        val endOfDay = getEndOfDay(dayTimestamp)
        financeDao.getByDate(startOfDay, endOfDay)
    }.onFailure {
        logger.logError(tag, "Failure to getByDay on FinanceLocalDataSource. $it")
    }

    suspend fun getByWeek(weekTimestamp: Long): Result<List<FinanceEntity>> = runCatching {
        val startOfWeek = getStartOfWeek(weekTimestamp)
        val endOfWeek = getEndOfWeek(weekTimestamp)
        financeDao.getByDate(startOfWeek, endOfWeek)
    }.onFailure {
        logger.logError(tag, "Failure to getByWeek on FinanceLocalDataSource. $it")
    }

    suspend fun getByMonth(monthTimestamp: Long): Result<List<FinanceEntity>> = runCatching {
        val startOfMonth = getStartOfMonth(monthTimestamp)
        val endOfMonth = getEndOfMonth(monthTimestamp)
        financeDao.getByDate(startOfMonth, endOfMonth)
    }.onFailure {
        logger.logError(tag, "Failure to getByMonth on FinanceLocalDataSource. $it")
    }

    // 📅 Methods to get the start and end of the day, week and month.
    private fun getStartOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfDay(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }

    private fun getStartOfWeek(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfWeek(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }

    private fun getStartOfMonth(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun getEndOfMonth(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }
}