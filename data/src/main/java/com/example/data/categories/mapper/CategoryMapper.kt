package com.example.data.categories.mapper

import com.example.data.databese.entity.CategoryEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.categories.model.FinanceTypes

fun Category.toEntity() = CategoryEntity(
    id = this.id,
    icon = this.icon,
    name = this.name,
    color = this.color,
    financeTypeId = financeType.id
)

fun CategoryEntity.toDomain(financeType: FinanceTypes): Category {
    return Category(
        id = id, icon = icon, name = name, color = color, financeType = financeType
    )
}