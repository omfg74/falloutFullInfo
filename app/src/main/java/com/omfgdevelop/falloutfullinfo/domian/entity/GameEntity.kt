package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id", index = true)
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String
)

