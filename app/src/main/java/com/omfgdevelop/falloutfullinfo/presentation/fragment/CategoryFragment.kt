package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentCategoryBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.ChildType
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.CategoryFragmentViewModel
import kotlinx.coroutines.launch


class CategoryFragment :
    BaseToolbarFragment<CategoryFragmentViewModel, FragmentCategoryBinding>(
        CategoryFragmentViewModel::class.java
    ) {


    private val args by navArgs<CategoryFragmentArgs>()

    private lateinit var rvAdapter: GenericListAdapter<Category>

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.gameType = args.gameId
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(ItemListFragment.CATEGORY) { key, bundle ->
            key.length
            if (bundle.getBundle("category") != null) {
                viewModel.currentCategory = bundle.getParcelable("category") as Category?
            }
        }
        setViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.currentCategoryList.observe(
            viewLifecycleOwner
        ) { t ->
            rvAdapter.submitList(t)
        }
    }

    override fun setToolBar() {
        super.setToolBar()
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


    override fun setViews() {
        with(binding) {
            recyclerView = rvCategory
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                @Suppress("UNCHECKED_CAST") @SuppressLint("ResourceType")
                rvAdapter =
                    object : GenericListAdapter<Category>(
                        R.layout.item_category,
                        bind = { item, holder, _ ->
                            with(holder.itemView) {
                                findViewById<TextView>(R.id.tv_category_name).text =
                                    item.category.name

                                holder.itemView.setOnClickListener {

                                    if (item.childType == ChildType.CATEGORY) {
                                        viewModel?.getChildCategory(item)
                                    } else if (item.childType == ChildType.ITEM) {
                                        navigateToItemFragment(item)
                                    }
                                }
                            }
                        }) {}.apply {
                        viewModel?.getChildCategory(viewModel?.currentCategory)
                    }
                adapter = rvAdapter
            }
        }
    }

    fun navigateToItemFragment(category: Category) {
        findNavController().navigate(
            CategoryFragmentDirections.actionNavigateFromCategoryFragmentToSkillListFrgmant(
                viewModel.gameType,
                category
            )
        )
    }

    override fun handleBackAction() {
        if (viewModel.parentCategoryId != null) {
            viewModel.getLoadBack()
        } else {
            findNavController().popBackStack()
        }
    }
}