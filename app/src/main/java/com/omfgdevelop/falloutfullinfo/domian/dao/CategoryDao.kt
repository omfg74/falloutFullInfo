package com.omfgdevelop.falloutfullinfo.domian.dao

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.omfgdevelop.falloutfullinfo.domian.entity.Category

@Dao
abstract class CategoryDao {
    @Transaction
    @Query(
        """
select distinct  c.category_id, c.name, c.parent_id, ctg.game_id,ctg.child_type
from Category  c 
inner JOIN category_to_game ctg  on c.category_id=ctg.category_id 
where c.parent_id is :id
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