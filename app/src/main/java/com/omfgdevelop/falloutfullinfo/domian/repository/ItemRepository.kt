package com.omfgdevelop.falloutfullinfo.domian.repository

import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemWithCategory
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun getItemListByCategoryId(categoryId: Long): Flow<List<ItemWithCategory>>

    suspend fun getItemById(id: Long): Flow<ItemEntity>
}