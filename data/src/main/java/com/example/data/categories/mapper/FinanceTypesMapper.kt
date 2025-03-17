package com.example.data.categories.mapper

import com.example.data.databese.entity.FinanceTypeEntity
import com.example.domain.module.categories.model.FinanceTypes


fun FinanceTypes.toEntity() = FinanceTypeEntity(
    id = this.id, name = this.name
)

fun FinanceTypeEntity.toDomain(): FinanceTypes {
    return FinanceTypes(id = id, name = name)
}