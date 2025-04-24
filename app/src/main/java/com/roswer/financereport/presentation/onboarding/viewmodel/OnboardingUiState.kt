package com.roswer.financereport.presentation.onboarding.viewmodel

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.roswer.domain.module.onboarding.model.Onboarding
import com.roswer.financereport.utils.Updatable

@Stable
interface OnboardingUiState {
    val onboarding : List<Onboarding>
    val error: Boolean
}

class MutableOnboardingUiState : OnboardingUiState, Updatable {

    override var onboarding: List<Onboarding> by mutableStateOf(emptyList())
    override var error: Boolean by mutableStateOf(false)

    companion object {
        fun buildFake() = MutableOnboardingUiState().apply {
            onboarding = listOf(Onboarding.buildFake(), Onboarding.buildFake())
            error = false
        }
    }
}