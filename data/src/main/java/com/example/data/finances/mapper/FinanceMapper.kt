package com.example.data.finances.mapper

import com.example.data.databese.entity.FinanceEntity
import com.example.domain.module.categories.model.Category
import com.example.domain.module.finances.models.Finance
import java.util.Date


fun Finance.toEntity() = FinanceEntity(
    id = this.id,
    date = this.date.time,
    amount = this.amount,
    categoryId = this.category.id,
    description = this.description ?: "",
    creationDate = this.creationDate.time
)

fun FinanceEntity.toDomain(category: Category): Finance {
    return Finance(
        id = this.id,
        date = Date(this.date),
        amount = this.amount,
        category = category,
        description = this.description,
        creationDate = Date(this.creationDate)
    )
}
