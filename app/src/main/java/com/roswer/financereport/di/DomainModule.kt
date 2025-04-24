package com.roswer.financereport.di

import com.roswer.domain.module.categories.repository.CategoryRepository
import com.roswer.domain.module.categories.repository.FinanceTypesRepository
import com.roswer.domain.module.categories.usecase.CategoryUseCase
import com.roswer.domain.module.categories.usecase.FinanceTypesUseCase
import com.roswer.domain.module.finances.repository.FinanceRepository
import com.roswer.domain.module.finances.usecase.FinanceUseCase
import com.roswer.domain.module.onboarding.repository.OnboardingRepository
import com.roswer.domain.module.onboarding.usecase.GetOnboardingUseCase
import com.roswer.domain.module.userPreferences.repository.UserPreferencesRepository
import com.roswer.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.roswer.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
import com.roswer.domain.utils.AndroidLogger
import com.roswer.domain.utils.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger = AndroidLogger()

    @Provides
    @Singleton
    fun provideGetOnboardingStatusUseCase(repository: UserPreferencesRepository): GetOnboardingStatusUseCase {
        return GetOnboardingStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetOnboardingCompletedUseCase(repository: UserPreferencesRepository): SetOnboardingCompletedUseCase {
        return SetOnboardingCompletedUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetOnboardingUseCase(repository: OnboardingRepository): GetOnboardingUseCase {
        return GetOnboardingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCategoryUseCase(repository: CategoryRepository): CategoryUseCase {
        return CategoryUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFinanceTypesUseCase(repository: FinanceTypesRepository): FinanceTypesUseCase {
        return FinanceTypesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFinanceUseCase(repository: FinanceRepository): FinanceUseCase {
        return FinanceUseCase(repository)
    }
}