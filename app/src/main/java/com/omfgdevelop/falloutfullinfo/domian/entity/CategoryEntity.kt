package com.omfgdevelop.falloutfullinfo.domian.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "category")
@Parcelize
data class CategoryEntity(
    @ColumnInfo(name = "category_id")
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "parent_id")
    val parentId: Long?
) : Parcelable