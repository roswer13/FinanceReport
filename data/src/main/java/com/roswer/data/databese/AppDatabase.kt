package com.roswer.data.databese

import androidx.room.Database
import androidx.room.RoomDatabase
import com.roswer.data.databese.dao.CategoryDao
import com.roswer.data.databese.dao.FinanceDao
import com.roswer.data.databese.dao.FinanceTypeDao
import com.roswer.data.databese.dao.UserDao
import com.roswer.data.databese.entity.CategoryEntity
import com.roswer.data.databese.entity.FinanceEntity
import com.roswer.data.databese.entity.FinanceTypeEntity
import com.roswer.data.databese.entity.UserEntity

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