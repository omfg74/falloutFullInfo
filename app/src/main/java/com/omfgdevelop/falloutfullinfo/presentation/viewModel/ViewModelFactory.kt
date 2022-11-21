package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(application) as T
        }
        if (modelClass.isAssignableFrom(CategoryFragmentViewModel::class.java)) {
            return CategoryFragmentViewModel(application) as T
        }
        throw java.lang.RuntimeException("Unknown view model class $modelClass")
    }
}