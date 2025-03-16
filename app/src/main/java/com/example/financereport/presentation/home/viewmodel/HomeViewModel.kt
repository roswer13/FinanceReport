package com.example.financereport.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.module.categories.usecase.CategoryUseCase
import com.example.domain.module.finances.models.Finance
import com.example.domain.module.finances.usecase.FinanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoriesUseCase: CategoryUseCase, private val financeUseCase: FinanceUseCase
) : ViewModel(), HomeUiAction {

    private val _channel = Channel<HomeUiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _uiState: MutableHomeUiState = MutableHomeUiState()
    val uiState: HomeUiState = _uiState

    init {
        loadHome()
    }

    private fun loadHome() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.categories = categoriesUseCase()
            _uiState.finances = financeUseCase.getFinancesList()
            _uiState.error = false
        }
    }

    override fun onCreateFinance(finance: Finance) {
        Log.i("HomeViewModel", "onCreateFinance $finance")
        viewModelScope.launch(Dispatchers.IO) {
            financeUseCase.saveFinance(finance)
            loadHome()
        }
    }
}