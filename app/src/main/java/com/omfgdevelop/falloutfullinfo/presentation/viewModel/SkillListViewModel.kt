package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.data.SkillRepositoryImpl
import com.omfgdevelop.falloutfullinfo.domian.entity.SkillWithCategory
import com.omfgdevelop.falloutfullinfo.domian.repository.SkillRepository
import com.omfgdevelop.falloutfullinfo.domian.usecases.GetSkillListUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

class SkillListViewModel(private val app: Application) : AndroidViewModel(app) {


    var gameId: Long = 0
    private val _skillList: MutableLiveData<List<SkillWithCategory>> = MutableLiveData()

    val skillList: LiveData<List<SkillWithCategory>>
        get() = _skillList

    private val getSkillRepository: SkillRepository =
        SkillRepositoryImpl.create((app as App).dataBase.skillDao())

    private val _getSkillListUseCase = GetSkillListUseCase(getSkillRepository)
    val getSkillListUseCase: GetSkillListUseCase
        get() = _getSkillListUseCase

    var categoryId: Long = 0

    fun getSkillList() {
        viewModelScope.launch {
            getSkillListUseCase(categoryId).collect() {
                _skillList.value = it
            }
        }
    }

}
