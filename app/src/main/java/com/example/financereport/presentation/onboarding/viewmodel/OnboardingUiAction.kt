package com.example.financereport.presentation.onboarding.viewmodel

interface OnboardingUiAction {
    fun onOnboardingCompleted()

    companion object {
        fun buildFake() = object : OnboardingUiAction {
            override fun onOnboardingCompleted() {}
        }
    }
}