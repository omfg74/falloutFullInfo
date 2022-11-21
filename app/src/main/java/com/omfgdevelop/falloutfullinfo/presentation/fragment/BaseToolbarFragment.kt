package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.omfgdevelop.falloutfullinfo.MainActivity
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.ViewModelFactory


abstract class BaseToolbarFragment<T : AndroidViewModel, B>(clazz: Class<T>) : Fragment() {

    open lateinit var toolbar: androidx.appcompat.app.ActionBar

    protected var _binding: B? = null

    protected val binding: B
        get() = _binding ?: throw java.lang.RuntimeException("binding is null")

    open val viewModel: T
        get() = _viewModel

    private val viewModelFactory by lazy {
        ViewModelFactory(requireActivity().application)
    }

    private val _viewModel: T by lazy {
        ViewModelProvider(this, viewModelFactory)[clazz]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolBar()
        setViews()
    }

    open fun setToolBar() {
        toolbar = (requireActivity() as MainActivity).supportActionBar
            ?: throw java.lang.RuntimeException("Toolbar not found")
    }

    abstract fun setViews()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
                handleBackAction()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    open fun handleBackAction() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}