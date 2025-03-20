package com.example.data.categories.mapper

import com.example.data.databese.entity.FinanceTypeEntity
import com.example.domain.module.categories.model.FinanceTypes

/**
 * Extension function to convert a `FinanceTypes` object to a `FinanceTypeEntity` object.
 *
 * @receiver FinanceTypes The domain model object to be converted.
 * @return FinanceTypeEntity The converted entity object.
 */
fun FinanceTypes.toEntity() = FinanceTypeEntity(
    id = this.id, name = this.name, icon = this.icon, color = this.color
)

/**
 * Extension function to convert a `FinanceTypeEntity` object to a `FinanceTypes` object.
 *
 * @receiver FinanceTypeEntity The entity object to be converted.
 * @return FinanceTypes The converted domain model object.
 */
fun FinanceTypeEntity.toDomain(): FinanceTypes {
    return FinanceTypes(id = id, name = name, icon = icon, color = color)
}