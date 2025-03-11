package com.example.data.databese.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.databese.entity.FinanceEntity

@Dao
interface FinanceDao : BaseDao<FinanceEntity> {
    @Query("SELECT * FROM finance")
    suspend fun getAll(): List<FinanceEntity>

    @Query("SELECT * FROM finance WHERE id = :id")
    suspend fun getById(id: Int): FinanceEntity

    @Query("SELECT * FROM finance WHERE date BETWEEN :start AND :end")
    suspend fun getByDate(start: Long, end: Long): List<FinanceEntity>
}