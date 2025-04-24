package com.roswer.domain.module.categories.repository

import com.roswer.domain.module.categories.model.Category

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}