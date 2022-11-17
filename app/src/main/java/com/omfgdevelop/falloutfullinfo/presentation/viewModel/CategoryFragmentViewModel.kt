package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.Game
import kotlinx.coroutines.flow.Flow

class CategoryFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = (application as App).dataBase.categoryDao()

    private val gameRepository = (application as App).dataBase.gameDao()

    fun getCategory(parentId: Long?): Flow<List<Category>> = repository.getCategoryList(parentId)


    fun getSelectedGame(gameId: Long): Flow<Game> = gameRepository.findGameById(gameId)
}

