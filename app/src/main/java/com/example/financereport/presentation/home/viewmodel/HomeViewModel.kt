package com.example.financereport.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.module.categories.usecase.CategoryUseCase
import com.example.domain.module.categories.usecase.FinanceTypesUseCase
import com.example.domain.module.finances.models.Finance
import com.example.domain.module.finances.usecase.FinanceUseCase
import com.example.domain.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val financeTypesUseCase: FinanceTypesUseCase,
    private val categoriesUseCase: CategoryUseCase,
    private val financeUseCase: FinanceUseCase,
    private val logger: Logger
) : ViewModel(), HomeUiAction {

    private val tag = this.javaClass.name

    private val _channel = Channel<HomeUiEvent>()
    val channel = _channel.receiveAsFlow()

    private val _uiState: MutableHomeUiState = MutableHomeUiState()
    val uiState: HomeUiState = _uiState

    init {
        loadHome()
    }

    private fun loadHome() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val financeTypes = financeTypesUseCase.getFinancesTypes()

                if (financeTypes.isEmpty()) {
                    _uiState.error = true
                    return@launch
                }

                _uiState.financeTypes = financeTypes
                _uiState.categories = categoriesUseCase.getCategoryList()
                _uiState.finances = financeUseCase.getFinancesList()
                _uiState.error = false
            }
        } catch (e: Exception) {
            logger.logError(tag, "Error loading home: ${e.message}")
            _uiState.error = true
        }
    }

    override fun onCreateFinance(finance: Finance) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                financeUseCase.saveFinance(finance)
                loadHome()
            }
        } catch (e: Exception) {
            logger.logError(tag, "Error creating finance: ${e.message}")
        }
    }
}