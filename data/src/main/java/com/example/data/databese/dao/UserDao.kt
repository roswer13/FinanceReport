package com.example.data.databese.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.data.databese.entity.UserEntity

@Dao
interface UserDao : BaseDao<UserEntity> {
    @Query("SELECT * FROM user WHERE id = 1 LIMIT 1")
    suspend fun getUser(): UserEntity?
}