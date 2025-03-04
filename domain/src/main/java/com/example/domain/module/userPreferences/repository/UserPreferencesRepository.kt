package com.example.domain.module.userPreferences.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    fun isOnboardingCompleted(): Flow<Boolean>
    suspend fun setOnboardingCompleted()
}