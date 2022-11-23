package com.omfgdevelop.falloutfullinfo.domian.repository

import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    suspend fun getCategoryListByParentId(parentId: Long, gameId: Long): Flow<List<Category>>

    suspend fun getCategoryById(id: Long, gameId: Long): Flow<Category>
}
