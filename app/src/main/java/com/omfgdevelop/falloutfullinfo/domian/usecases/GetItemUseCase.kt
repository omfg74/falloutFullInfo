package com.omfgdevelop.falloutfullinfo.domian.usecases

import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class GetItemUseCase(private val itemRepository: ItemRepository) {

    suspend operator fun invoke(categoryId: Long): Flow<ItemEntity> {
        return itemRepository.getItemById(categoryId)
    }
}