package com.omfgdevelop.falloutfullinfo.domian.entity

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class CategoryDao {
    @Transaction
    @Query(
        """
select distinct  *
from Category  c 
inner JOIN category_to_game ctg  on c.category_id=ctg.category_id
where c.parent_id is  :id
AND ctg.game_id = :gameId
        """
    )
    abstract fun getChildCategoryList(id: Long?, gameId: Long): Flow<List<Category>>

    @Query(
        """
         select distinct  *
from Category  c 
inner JOIN category_to_game ctg  on c.category_id=ctg.category_id
where c.category_id is  :id
AND ctg.game_id = :gameId
    """
    )
    abstract fun getCategoryById(id: Long?, gameId: Long): Flow<Category>
}