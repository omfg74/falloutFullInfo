package com.omfgdevelop.falloutfullinfo.domian.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.TypeConverters
import com.omfgdevelop.falloutfullinfo.domian.ChildTypeTypeConverter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    @Embedded
    val category: CategoryEntity,

    @ColumnInfo(name = "child_type")
    @TypeConverters(value = [ChildTypeTypeConverter::class])
    val childType: ChildType


) : Parcelable