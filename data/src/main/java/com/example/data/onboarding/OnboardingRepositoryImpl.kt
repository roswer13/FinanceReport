package com.example.data.onboarding

import android.content.Context
import com.example.data.R
import com.example.domain.module.onboarding.model.Onboarding
import com.example.domain.module.onboarding.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val context: Context
) : OnboardingRepository {

    override fun getOnboardingData(language: String): List<Onboarding> {
        val resources = context.resources
        return listOf(
            Onboarding(
                id = 1,
                title = resources.getString(R.string.app_name),
                description = "1. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                uri = "https://fakeimg.pl/250",
            ),
            Onboarding(
                id = 2,
                title = resources.getString(R.string.app_name),
                description = "2. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                uri = "https://fakeimg.pl/250",
            ),
        )
    }
}