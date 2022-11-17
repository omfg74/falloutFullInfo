package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CategoryFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var _parentCategoryId: Long? = null
    val parentCategoryId: Long?
        get() = _parentCategoryId

    private var _currentId: Long? = null
    val currentId: Long?
        get() = _currentId

    private val repository = (application as App).dataBase.categoryDao()

    private val gameRepository = (application as App).dataBase.gameDao()

    private val _category: MutableLiveData<List<Category>> = MutableLiveData()

    val category: LiveData<List<Category>>
        get() = _category


    fun getCategory(id: Long?) {
        _currentId = id;
        viewModelScope.launch {
            repository.getCategoryList(id).collect() {
                _category.value = it
                //todo check if exists
                if (it.isNotEmpty()) {
                    _parentCategoryId = it[0].parentId
                } else _parentCategoryId = currentId
            }
        }

    }


    fun getSelectedGame(gameId: Long): Flow<Game> = gameRepository.findGameById(gameId)

    fun getParentCategory() {
        viewModelScope.launch {
            repository.getParentCategory(parentCategoryId).collect() {
                getCategory(it.parentId)
            }
        }
    }
}

