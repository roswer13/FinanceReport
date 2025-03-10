package com.example.financereport.di

import android.content.Context
import androidx.room.Room
import com.example.data.categories.repository.CategoryLocalDataSource
import com.example.data.categories.repository.CategoryRepositoryImpl
import com.example.data.databese.AppDatabase
import com.example.data.databese.dao.CategoryDao
import com.example.data.databese.dao.UserDao
import com.example.data.onboarding.OnboardingRepositoryImpl
import com.example.data.user.repository.UserLocalDataSource
import com.example.data.userPreferences.repository.UserPreferencesRepositoryImpl
import com.example.domain.module.categories.repository.CategoryRepository
import com.example.domain.module.onboarding.repository.OnboardingRepository
import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import com.example.domain.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext, AppDatabase::class.java, "finance_report"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providePetDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(dao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(dao: CategoryDao, logger: Logger): CategoryLocalDataSource {
        return CategoryLocalDataSource(dao, logger)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepositoryImpl(context: Context): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideOnboardingRepositoryImpl(context: Context): OnboardingRepository {
        return OnboardingRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideCategoryRepositoryImpl(
        context: Context, localDataSource: CategoryLocalDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(context, localDataSource)
    }
}