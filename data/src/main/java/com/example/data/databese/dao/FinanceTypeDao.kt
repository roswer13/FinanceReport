package com.example.data.databese.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.databese.entity.FinanceTypeEntity

@Dao
interface FinanceTypeDao : BaseDao<FinanceTypeEntity> {
    @Query("SELECT * FROM finance_type")
    suspend fun getAll(): List<FinanceTypeEntity>
}