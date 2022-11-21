package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.MainActivity
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentCategoryBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.ChildType
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.CategoryFragmentViewModel
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.ViewModelFactory
import kotlinx.coroutines.launch


class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null

    private val args by navArgs<CategoryFragmentArgs>()

    private lateinit var rvAdapter: GenericListAdapter<Category>

    private val binding: FragmentCategoryBinding
        get() = _binding ?: throw java.lang.RuntimeException("binding is null")

    private lateinit var recyclerView: RecyclerView

    private lateinit var toolbar: androidx.appcompat.app.ActionBar

    private val viewModelFactory by lazy {
        ViewModelFactory(requireActivity().application)
    }

    private val viewModel: CategoryFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CategoryFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.gameType = args.gameId
        setToolBar()
        setViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentCategory.observe(
            viewLifecycleOwner
        ) { t ->
            rvAdapter.submitList(t)
        }
    }


    private fun setToolBar() {
        toolbar = (requireActivity() as MainActivity).supportActionBar
            ?: throw java.lang.RuntimeException("Toolbar not found")

        viewModel.parentCategory.observe(viewLifecycleOwner) {
            setToolBarTitle(name = it?.category?.name)
        }
    }

    private fun setToolBarTitle(name: String?) {
        with(toolbar) {
            if (name == null) {
                lifecycle.coroutineScope.launch {
                    viewModel.getSelectedGame(args.gameId).collect() {
                        title = it.name
                    }
                }
            } else {
                title = name
            }
        }
    }


    private fun setViews() {
        with(binding) {
            recyclerView = rvCategory
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                @Suppress("UNCHECKED_CAST") @SuppressLint("ResourceType")
                rvAdapter =
                    object : GenericListAdapter<Category>(
                        R.layout.item_category,
                        bind = { item, holder, itemCount ->
                            with(holder.itemView) {
                                findViewById<TextView>(R.id.tv_category_name).text =
                                    item.category.name

                                holder.itemView.setOnClickListener {
                                    if (item.childType == ChildType.CATEGORY) {
                                        viewModel?.getChildCategory(item)
                                    } else {
                                        navigateToItemFragment()
                                    }
                                }
                            }
                        }) {}.apply {
                        viewModel?.getChildCategory(null)
                    }
                adapter = rvAdapter
            }
        }
    }

    fun navigateToItemFragment() {
Toast.makeText(requireContext(),"Navigate to item",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {

                handleBackAction()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    private fun handleBackAction() {
        if (viewModel.parentCategoryId != null) {
            viewModel.getLoadBack()
        } else {
            findNavController().popBackStack()
        }
    }
}