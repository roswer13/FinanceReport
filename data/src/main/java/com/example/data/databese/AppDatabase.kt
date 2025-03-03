package com.example.data.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.databese.dao.UserDao
import com.example.data.databese.entity.UserEntity

@Database(
    version = 1, entities = [UserEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}