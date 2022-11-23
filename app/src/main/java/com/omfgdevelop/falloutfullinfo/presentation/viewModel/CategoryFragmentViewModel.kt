package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.GameEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var _parentCategoryId: Long? = null
    val parentCategoryId: Long?
        get() = _parentCategoryId

    //    private var _currentCategory: Category? = null
    var currentCategory: Category? = null

    private var _parentCategory: MutableLiveData<Category?> = MutableLiveData()
    val parentCategory: LiveData<Category?>
        get() = _parentCategory

    var gameType: Long = 0

    private val repository = (application as App).dataBase.categoryDao()

    private val gameRepository = (application as App).dataBase.gameDao()

    private val _currentCategoryList: MutableLiveData<List<Category>> = MutableLiveData()

    val currentCategoryList: LiveData<List<Category>>
        get() = _currentCategoryList


    fun getChildCategory(category: Category?) {
        currentCategory = category
        _parentCategory.value = category
        viewModelScope.launch {
            repository.getChildCategoryList(category?.category?.id, gameType).collect() {
                _currentCategoryList.value = it
                _parentCategoryId = if (it.isNotEmpty()) {
                    it[0].category.parentId
                } else null
            }
        }
    }

    fun getCategoryById(id: Long) {
        viewModelScope.launch {
            repository.getCategoryById(_parentCategory.value?.category?.id, gameType).collect() {
                currentCategory
            }
        }
    }

    fun getSelectedGame(gameId: Long): Flow<GameEntity> = gameRepository.findGameById(gameId)

    fun getLoadBack() {
        viewModelScope.launch {
            repository.getCategoryById(_parentCategory.value?.category?.id, gameType).collect() {
                _parentCategory.value = it
                viewModelScope.launch {
                    repository.getCategoryById(it.category.parentId, gameType).collect() {
                        getChildCategory(it)
                    }
                }
            }
        }
    }


}

