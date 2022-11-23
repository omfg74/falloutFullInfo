package com.omfgdevelop.falloutfullinfo.data

import com.omfgdevelop.falloutfullinfo.domian.dao.ItemDao
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemWithCategory
import com.omfgdevelop.falloutfullinfo.domian.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

object ItemRepositoryImpl : ItemRepository {

    private var itemRepository: ItemRepository? = null

    private lateinit var dao: ItemDao

    fun create(dao: ItemDao): ItemRepository {
        this.dao = dao
        if (this.itemRepository == null) {
            itemRepository = ItemRepositoryImpl
        }
        return itemRepository!!
    }

    override suspend fun getItemListByCategoryId(categoryId: Long): Flow<List<ItemWithCategory>> {
        return dao.getItemListByCategoryId(categoryId)
    }

    override suspend fun getItemById(id: Long): Flow<ItemEntity> {
        return dao.getItemById(id)
    }
}