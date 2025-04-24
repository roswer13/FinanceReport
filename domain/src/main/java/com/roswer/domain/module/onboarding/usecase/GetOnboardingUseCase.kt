package com.roswer.domain.module.onboarding.usecase

import com.roswer.domain.module.onboarding.model.Onboarding
import com.roswer.domain.module.onboarding.repository.OnboardingRepository
import javax.inject.Inject

class GetOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(language: String): List<Onboarding> =
        repository.getOnboardingData(language = language)
}