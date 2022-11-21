package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "skill")
data class SkillEntity(
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_name")
    val imageName: String,

    @ColumnInfo(name = "parent_category")
    val parentCategoryId: Long

)