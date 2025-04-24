package com.roswer.financereport.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roswer.domain.module.categories.usecase.CategoryUseCase
import com.roswer.domain.module.categories.usecase.FinanceTypesUseCase
import com.roswer.domain.module.finances.models.Finance
import com.roswer.domain.module.finances.usecase.FinanceUseCase
import com.roswer.domain.utils.Logger
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
                _uiState.finances = financeUseCase.getFinancesListByMonthYear(
                    month = _uiState.month, year = _uiState.year
                )
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

    override fun onUpdateFinance(finance: Finance) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                financeUseCase.updateFinance(finance)
                loadHome()
            }
        } catch (e: Exception) {
            logger.logError(tag, "Error updating finance: ${e.message}")
        }
    }

    override fun onDeleteFinance(finance: Finance) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                financeUseCase.deleteFinance(finance)
                loadHome()
            }
        } catch (e: Exception) {
            logger.logError(tag, "Error deleting finance: ${e.message}")
        }
    }

    override fun findFinancesByMonthAndYear(month: Int, year: Int) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                _uiState.finances = financeUseCase.getFinancesListByMonthYear(
                    month = month, year = year
                )
            }
        } catch (e: Exception) {
            logger.logError(tag, "Error loading home: ${e.message}")
            _uiState.error = true
        }
    }
}