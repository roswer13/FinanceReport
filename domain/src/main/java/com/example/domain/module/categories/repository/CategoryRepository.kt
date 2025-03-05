package com.example.domain.module.categories.repository

import com.example.domain.module.categories.model.Category

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}