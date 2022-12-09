package com.omfgdevelop.falloutfullinfo.domian.usecases

import com.omfgdevelop.falloutfullinfo.domian.entity.ItemWithCategory
import com.omfgdevelop.falloutfullinfo.domian.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetItemListUseCase(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(categoryId: Long, gameId: Long): Flow<List<ItemWithCategory>> {
        return itemRepository.getItemListByCategoryId(categoryId, gameId)
    }
}