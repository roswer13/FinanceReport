package com.example.financereport.di

import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import com.example.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.example.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
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
    fun provideGetOnboardingStatusUseCase(repository: UserPreferencesRepository): GetOnboardingStatusUseCase {
        return GetOnboardingStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetOnboardingCompletedUseCase(repository: UserPreferencesRepository): SetOnboardingCompletedUseCase {
        return SetOnboardingCompletedUseCase(repository)
    }
}