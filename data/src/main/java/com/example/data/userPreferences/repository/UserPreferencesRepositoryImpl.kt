package com.example.data.userPreferences.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.domain.module.userPreferences.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "settings")

class UserPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
) : UserPreferencesRepository {
    companion object {
        private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_completed")
    }

    override fun isOnboardingCompleted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences -> preferences[ONBOARDING_KEY] ?: false }
    }

    override suspend fun setOnboardingCompleted() {
        context.dataStore.edit { preferences -> preferences[ONBOARDING_KEY] = true }
    }
}