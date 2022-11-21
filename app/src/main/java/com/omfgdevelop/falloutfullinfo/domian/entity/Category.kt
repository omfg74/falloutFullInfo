package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.*
import com.omfgdevelop.falloutfullinfo.domian.ChildTypeTypeConverter

data class Category(
    @Embedded
    val category: CategoryEntity,

    @ColumnInfo(name = "child_type")
    @TypeConverters(value = [ChildTypeTypeConverter::class])
    val childType: ChildType


)