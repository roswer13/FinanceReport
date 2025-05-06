package com.roswer.data.categories.mapper

import com.roswer.data.databese.entity.CategoryEntity
import com.roswer.domain.module.categories.model.Category
import com.roswer.domain.module.categories.model.FinanceTypes

fun Category.toEntity() = CategoryEntity(
    id = this.id,
    icon = this.icon,
    emojiIcon = this.emojiIcon,
    name = this.name,
    color = this.color,
    financeTypeId = financeType.id
)

fun CategoryEntity.toDomain(financeType: FinanceTypes): Category {
    return Category(
        id = id,
        icon = icon,
        emojiIcon = emojiIcon,
        name = name,
        color = color,
        financeType = financeType
    )
}