package com.omfgdevelop.falloutfullinfo.domian.dao

import androidx.room.Dao
import androidx.room.Query
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query(
        """
       select s.id, 
s.name,
s.description,
s.parent_category,
s.image_name,
c.category_id,
c.name as category_name,
c.parent_id as category_parent_id 
from item s 
join item_to_game itg on itg.item_id=s.id
join category c on s.parent_category=c.category_id
join game g on itg.game_id=g.game_id
where c.category_id=:id and g.game_id=:gameId"""
    )
    fun getItemListByCategoryId(id: Long, gameId: Long): Flow<List<ItemWithCategory>>


    @Query(
        """
         select s.id, 
s.name,
s.description,
s.parent_category,
s.image_name,
c.category_id,
c.name as category_name,
c.parent_id as category_parent_id 
from item s 
join item_to_game itg on itg.item_id=s.id
join category c on s.parent_category=c.category_id
join game g on itg.game_id=g.game_id
where s.id=:id and g.game_id=:gameId"""
    )
    fun getItemById(id: Long, gameId: Long): Flow<ItemEntity>
}