package com.roswer.financereport.presentation.onboarding.viewmodel

interface OnboardingUiAction {
    fun onOnboardingCompleted()

    companion object {
        fun buildFake() = object : OnboardingUiAction {
            override fun onOnboardingCompleted() {}
        }
    }
}