package com.example.data.onboarding

import com.example.domain.module.onboarding.model.Onboarding

interface OnboardingLocalDataSource {
    fun getOnboardingData(language: String) : List<Onboarding>
}