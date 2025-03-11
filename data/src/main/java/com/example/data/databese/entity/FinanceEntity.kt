package com.example.data.databese.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance")
class FinanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name="date")
    val date: Long,
    @ColumnInfo(name="amount")
    val amount: Double,
    @ColumnInfo(name="category_id")
    val categoryId: Int,
    @ColumnInfo(name="type")
    val type: String,
    @ColumnInfo(name="description")
    val description: String,
    @ColumnInfo(name="creation_date")
    val creationDate: Long
)