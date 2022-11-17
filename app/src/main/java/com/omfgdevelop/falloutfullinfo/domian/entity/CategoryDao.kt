package com.omfgdevelop.falloutfullinfo.domian.entity

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("select * from Category where parent_id is :id")
    fun getCategoryList(id: Long?): Flow<List<Category>>
}