package com.example.data.categories.mapper

import com.example.data.databese.entity.CategoryEntity
import com.example.domain.module.categories.model.Category

fun Category.toEntity() = CategoryEntity(
    id, icon, name, color
)

fun CategoryEntity.toDomain(): Category {
    return Category(id, icon, name, color)
}

fun List<CategoryEntity>.toDomain() = map { it.toDomain() }