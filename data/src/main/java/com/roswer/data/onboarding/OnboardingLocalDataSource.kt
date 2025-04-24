package com.roswer.data.onboarding

import com.roswer.domain.module.onboarding.model.Onboarding

interface OnboardingLocalDataSource {
    fun getOnboardingData(language: String) : List<Onboarding>
}