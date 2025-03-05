package com.example.domain.module.categories.usecase

import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.repository.CategoryRepository
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> = repository.getCategoryList()
}