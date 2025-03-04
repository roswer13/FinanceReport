package com.example.domain.module.onboarding.usecase

import com.example.domain.module.onboarding.model.Onboarding
import com.example.domain.module.onboarding.repository.OnboardingRepository
import javax.inject.Inject

class GetOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(language: String): List<Onboarding> =
        repository.getOnboardingData(language = language)
}