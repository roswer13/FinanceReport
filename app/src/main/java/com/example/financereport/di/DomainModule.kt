package com.example.financereport.di

import com.example.domain.module.categories.repository.CategoryRepository
import com.example.domain.module.categories.usecase.CategoryUseCase
import com.example.domain.module.onboarding.repository.OnboardingRepository
import com.example.domain.module.onboarding.usecase.GetOnboardingUseCase
import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import com.example.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.example.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
import com.example.domain.utils.AndroidLogger
import com.example.domain.utils.Logger
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
}