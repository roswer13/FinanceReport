package com.example.data.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.dao.FinanceDao
import com.example.data.databese.dao.FinanceTypeDao
import com.example.data.databese.dao.UserDao
import com.example.data.databese.entity.CategoryEntity
import com.example.data.databese.entity.FinanceEntity
import com.example.data.databese.entity.FinanceTypeEntity
import com.example.data.databese.entity.UserEntity

@Database(
    version = 1,
    entities = [
        UserEntity::class,
        CategoryEntity::class,
        FinanceEntity::class,
        FinanceTypeEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun financeDao(): FinanceDao
    abstract fun financeTypeDao(): FinanceTypeDao
}