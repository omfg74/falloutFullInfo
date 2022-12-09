package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "item_to_game", primaryKeys = ["game_id", "item_id"])
data class ItemToGame(
    @ColumnInfo(name = "game_id")
    val gameId: Long,
    @ColumnInfo(name = "item_id")
    val itemId: Long,
    @ColumnInfo(name = "parent_category")
    val parentCategory: Long
)
