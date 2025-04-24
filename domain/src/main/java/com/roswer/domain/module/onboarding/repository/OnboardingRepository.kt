package com.roswer.domain.module.onboarding.repository

import com.roswer.domain.module.onboarding.model.Onboarding

interface OnboardingRepository {
    fun getOnboardingData(language: String): List<Onboarding>
}