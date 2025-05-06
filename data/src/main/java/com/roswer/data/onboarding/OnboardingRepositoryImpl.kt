package com.roswer.data.onboarding

import android.content.Context
import com.roswer.data.R
import com.roswer.domain.module.onboarding.model.Onboarding
import com.roswer.domain.module.onboarding.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val context: Context
) : OnboardingRepository {

    override fun getOnboardingData(language: String): List<Onboarding> {
        val resources = context.resources
        return listOf(
            Onboarding(
                id = 1,
                title = resources.getString(R.string.onboarding_title_1),
                description = resources.getString(R.string.onboarding_description_1),
                icon = "💳",
                uri = "https://fakeimg.pl/250",
            ),
            Onboarding(
                id = 2,
                title = resources.getString(R.string.onboarding_title_2),
                description = resources.getString(R.string.onboarding_description_2),
                icon = "📊",
                uri = "https://fakeimg.pl/250",
            ),
            Onboarding(
                id = 3,
                title = resources.getString(R.string.onboarding_title_3),
                description = resources.getString(R.string.onboarding_description_3),
                icon = "💰",
                uri = "https://fakeimg.pl/250",
            ),
        )
    }
}