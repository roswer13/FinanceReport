package com.example.financereport.presentation.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.module.onboarding.usecase.GetOnboardingUseCase
import com.example.domain.module.userPreferences.usecase.GetOnboardingStatusUseCase
import com.example.domain.module.userPreferences.usecase.SetOnboardingCompletedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val setOnboardingCompletedUseCase: SetOnboardingCompletedUseCase,
    private val getOnboardingUseCase: GetOnboardingUseCase
) : ViewModel(), OnboardingUiAction {

    private val _uiState: MutableOnboardingUiState = MutableOnboardingUiState()
    val uiState: OnboardingUiState = _uiState

    private val _channel = Channel<OnboardingUiEvent>()
    val channel: Flow<OnboardingUiEvent> = _channel.receiveAsFlow()

    init {
        getOnboardingPages()
    }

    private fun getOnboardingPages() {
        _uiState.onboarding = getOnboardingUseCase("en")
        _uiState.error = false
    }

    override fun onOnboardingCompleted() {
        viewModelScope.launch(Dispatchers.Default) {
            _channel.send(OnboardingUiEvent.OnOnboardingCompleted)
        }
    }
}