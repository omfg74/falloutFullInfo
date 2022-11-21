package com.omfgdevelop.falloutfullinfo.domian.entity

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("select * from Game")
    fun findAllGames(): Flow<List<Game>>

    @Query("select * from Game where game_id is :id")
    fun findGameById(id: Long): Flow<Game>
}