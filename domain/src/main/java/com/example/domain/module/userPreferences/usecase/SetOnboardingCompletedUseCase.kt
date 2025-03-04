package com.example.domain.module.userPreferences.usecase

import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import javax.inject.Inject

class SetOnboardingCompletedUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    suspend operator fun invoke() = repository.setOnboardingCompleted()
}