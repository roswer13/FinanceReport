package com.roswer.domain.module.categories.usecase

import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.module.categories.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    /**
     * Get the list of categories.
     */
    suspend fun getCategoryList(): List<Category> = repository.getCategoryList()
}