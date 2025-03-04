package com.example.domain.module.onboarding.repository

import com.example.domain.module.onboarding.model.Onboarding

interface OnboardingRepository {
    fun getOnboardingData(language: String): List<Onboarding>
}