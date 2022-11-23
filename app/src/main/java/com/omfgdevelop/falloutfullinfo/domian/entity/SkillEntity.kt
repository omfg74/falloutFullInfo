package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "skill")
data class SkillEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_name")
    val imageName: String?,

    @ColumnInfo(name = "parent_category")
    val parentCategoryId: Long?

)