package com.example.domain.module.userPreferences.usecase

import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(
    private val repository: UserPreferencesRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted()
}