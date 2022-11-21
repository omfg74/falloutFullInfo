package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "category_to_game", primaryKeys = ["game_id", "category_id"])
data class CategoryToGame(

    @ColumnInfo(name = "game_id")
    val gameId: Long,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @ColumnInfo(name = "child_type", defaultValue = "0")
    val childType: Long
)
