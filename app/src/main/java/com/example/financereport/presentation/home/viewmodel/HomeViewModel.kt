package com.example.financereport.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.module.categories.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val categoriesUseCase: CategoryUseCase
) : ViewModel() {

    private val _uiState: MutableHomeUiState = MutableHomeUiState()
    val uiState: HomeUiState = _uiState

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch(Dispatchers.IO){
            _uiState.categories = categoriesUseCase()
            _uiState.error = false
        }
    }
}