package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.domian.entity.GameEntity

class MainFragmentViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = (application as App).dataBase

    private val gameDao = database.gameDao()

    fun getAllGames(): kotlinx.coroutines.flow.Flow<List<GameEntity>> =
        gameDao.findAllGames()


}
