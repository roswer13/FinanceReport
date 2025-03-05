package com.example.data.databese.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.databese.entity.CategoryEntity

@Dao
interface CategoryDao : BaseDao<CategoryEntity> {

    @Query("SELECT * FROM category")
    suspend fun getAll(): List<CategoryEntity>

}