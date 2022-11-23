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
from skill s join category c on s.parent_category=c.category_id
where c.category_id=:id"""
    )
    fun getItemListByCategoryId(id: Long): Flow<List<ItemWithCategory>>


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
from skill s join category c on s.parent_category=c.category_id
where s.id=:id"""
    )
    fun getItemById(id: Long): Flow<ItemEntity>
}