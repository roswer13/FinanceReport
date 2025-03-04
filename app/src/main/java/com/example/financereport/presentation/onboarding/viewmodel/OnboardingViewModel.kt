package com.example.financereport.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.example.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase
) : ViewModel() {

    val isOnboardingCompleted: StateFlow<Boolean> =
        getOnboardingStatusUseCase().stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun completeOnboarding() {
        viewModelScope.launch {
            setOnboardingCompletedUseCase()
        }
    }
}