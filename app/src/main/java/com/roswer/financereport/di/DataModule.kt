package com.roswer.financereport.di

import android.content.Context
import androidx.room.Room
import com.roswer.data.categories.repository.CategoryLocalDataSource
import com.roswer.data.categories.repository.CategoryRepositoryImpl
import com.roswer.data.categories.repository.FinanceTypesLocalDataSource
import com.roswer.data.categories.repository.FinanceTypesRepositoryImpl
import com.roswer.data.databese.AppDatabase
import com.roswer.data.databese.dao.CategoryDao
import com.roswer.data.databese.dao.FinanceDao
import com.roswer.data.databese.dao.FinanceTypeDao
import com.roswer.data.databese.dao.UserDao
import com.roswer.data.finances.repository.FinanceLocalDataSource
import com.roswer.data.finances.repository.FinanceRepositoryImpl
import com.roswer.data.onboarding.OnboardingRepositoryImpl
import com.roswer.data.user.repository.UserLocalDataSource
import com.roswer.data.userPreferences.repository.UserPreferencesRepositoryImpl
import com.roswer.domain.module.categories.repository.CategoryRepository
import com.roswer.domain.module.categories.repository.FinanceTypesRepository
import com.roswer.domain.module.finances.repository.FinanceRepository
import com.roswer.domain.module.onboarding.repository.OnboardingRepository
import com.roswer.domain.module.userPreferences.repository.UserPreferencesRepository
import com.roswer.domain.utils.Logger
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
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Singleton
    @Provides
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }

    @Singleton
    @Provides
    fun provideFinanceTypeDao(db: AppDatabase): FinanceTypeDao {
        return db.financeTypeDao()
    }

    @Singleton
    @Provides
    fun provideFinanceDao(db: AppDatabase): FinanceDao {
        return db.financeDao()
    }

    @Provides
    @Singleton
    fun provideUserLocalDataSource(dao: UserDao): UserLocalDataSource {
        return UserLocalDataSource(dao)
    }

    @Provides
    @Singleton
    fun provideCategoryLocalDataSource(
        dao: CategoryDao, logger: Logger
    ): CategoryLocalDataSource {
        return CategoryLocalDataSource(dao, logger)
    }

    @Provides
    @Singleton
    fun provideFinanceTypesLocalDataSource(
        dao: FinanceTypeDao, logger: Logger
    ): FinanceTypesLocalDataSource {
        return FinanceTypesLocalDataSource(dao, logger)
    }

    @Provides
    @Singleton
    fun provideFinanceLocalDataSource(dao: FinanceDao, logger: Logger): FinanceLocalDataSource {
        return FinanceLocalDataSource(dao, logger)
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
        context: Context,
        financeTypesLocalDataSource: FinanceTypesLocalDataSource,
        localDataSource: CategoryLocalDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(context, financeTypesLocalDataSource, localDataSource)
    }

    @Provides
    @Singleton
    fun provideFinanceTypesRepositoryImpl(
        context: Context, localDataSource: FinanceTypesLocalDataSource
    ): FinanceTypesRepository {
        return FinanceTypesRepositoryImpl(context, localDataSource)
    }

    @Provides
    @Singleton
    fun provideFinanceRepositoryImpl(
        localDataSource: FinanceLocalDataSource,
        financeTypesLocalDataSource: FinanceTypesLocalDataSource,
        categoryLocalDataSource: CategoryLocalDataSource
    ): FinanceRepository {
        return FinanceRepositoryImpl(
            localDataSource, financeTypesLocalDataSource, categoryLocalDataSource
        )
    }
}