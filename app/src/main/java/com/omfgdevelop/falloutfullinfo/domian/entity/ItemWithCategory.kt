package com.omfgdevelop.falloutfullinfo.domian.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemWithCategory(
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "image_name")
    val imageName: String,

    @ColumnInfo(name = "parent_category")
    val parentCategoryId: Long,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    @ColumnInfo(name = "category_parent_id")
    val parentId: Long?
) : Parcelable