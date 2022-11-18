package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "category")
data class CategoryEntity(
    @ColumnInfo(name = "category_id")
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "parent_id")
    val parentId: Long?
)