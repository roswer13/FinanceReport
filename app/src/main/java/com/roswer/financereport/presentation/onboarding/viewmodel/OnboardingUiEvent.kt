package com.roswer.financereport.presentation.onboarding.viewmodel

sealed interface OnboardingUiEvent {
    object OnOnboardingCompleted : OnboardingUiEvent
}