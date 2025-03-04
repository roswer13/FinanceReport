package com.example.financereport.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import com.example.domain.module.onboarding.usecase.GetOnboardingUseCase
import com.example.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.example.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
    private val getOnboardingUseCase: GetOnboardingUseCase
) : ViewModel() {

    private val _uiState: MutableOnboardingUiState = MutableOnboardingUiState()
    val uiState: OnboardingUiState = _uiState

    init {
        getOnboardingPages()
    }

    private fun getOnboardingPages() {
        _uiState.onboarding = getOnboardingUseCase("en")
        _uiState.error = false
    }
}