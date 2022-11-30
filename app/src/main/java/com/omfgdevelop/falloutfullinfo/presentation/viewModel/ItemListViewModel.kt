package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.data.CategoryRepositoryImpl
import com.omfgdevelop.falloutfullinfo.data.ItemRepositoryImpl
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.repository.CategoryRepository
import com.omfgdevelop.falloutfullinfo.domian.repository.ItemRepository
import com.omfgdevelop.falloutfullinfo.domian.usecases.GetItemListUseCase
import kotlinx.coroutines.launch

class ItemListViewModel(private val app: Application) : AndroidViewModel(app) {


    var gameId: Long = 0

    private val getItemRepository: ItemRepository =
        ItemRepositoryImpl.create((app as App).dataBase.ItemDao())

    private val categoryRepository: CategoryRepository =
        CategoryRepositoryImpl.create((app as App).dataBase.categoryDao())


    private val _getSkillListUseCase by lazy { GetItemListUseCase(getItemRepository) }
    val getSkillListUseCase: GetItemListUseCase
        get() = _getSkillListUseCase

    val category: MutableLiveData<Category> = MutableLiveData()

    fun getCategoryById(parentCategoryId: Long?) {
        viewModelScope.launch {
            categoryRepository.getCategoryById(parentCategoryId ?: return@launch, gameId)
                .collect() {
                    category.postValue(it)
                }
        }
    }
}
