package com.example.financereport.presentation.onboarding.viewmodel

sealed interface OnboardingUiEvent {
    object OnOnboardingCompleted : OnboardingUiEvent
}