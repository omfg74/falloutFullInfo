package com.omfgdevelop.falloutfullinfo.domian.dao

import androidx.room.Dao
import androidx.room.Query
import com.omfgdevelop.falloutfullinfo.domian.entity.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("select * from Game")
    fun findAllGames(): Flow<List<GameEntity>>

    @Query("select * from Game where game_id is :id")
    fun findGameById(id: Long): Flow<GameEntity>
}