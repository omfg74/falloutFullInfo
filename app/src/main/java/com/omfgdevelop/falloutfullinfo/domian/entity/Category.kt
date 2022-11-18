package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class Category(
    @Embedded
    val category: CategoryEntity,

//    @Relation(
//        parentColumn = "id",
//        entityColumn = "game_id",
//        associateBy = Junction(CategoryToGame::class)
//    )
//    val game: Game,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id",
        associateBy = Junction(CategoryToGame::class)
    )
    val childType: CategoryToGame


)