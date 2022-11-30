package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentSkillListBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemWithCategory
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.ItemListViewModel
import com.omfgdevelop.falloutfullinfo.utils.Utils
import kotlinx.coroutines.launch


class ItemListFragment :
    BaseToolbarFragment<ItemListViewModel, FragmentSkillListBinding>(ItemListViewModel::class.java) {

    companion object {
        const val CATEGORY: String = "CATEGORY"
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var rvAdapter: GenericListAdapter<ItemWithCategory>

    private val args by navArgs<ItemListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(ItemFragment.ITEM) { key, bundle ->
            if (bundle.getBundle("game_id") != null) {
                viewModel.gameId = bundle.getLong("game_id")
            }
            if (bundle.getBundle("category") != null) {
                viewModel.getCategoryById(
                    (bundle.getParcelable("category") as? ItemEntity)?.parentCategoryId,
                )
            }
        }
        if (viewModel.category.value == null)
            viewModel.category.value = args.category
        if (viewModel.gameId == 0L)
            viewModel.gameId = args.gameId
        setToolBarTitle(viewModel.category.value?.category?.name)
        observeViewModel()
        updateList()
    }

    private fun observeViewModel() {
        viewModel.category.observe(viewLifecycleOwner) {
            updateList()
        }
    }


    @SuppressLint("ResourceType")
    override fun setViews() {
        with(binding) {
            recyclerView = rvSkillList
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                rvAdapter = object :
                    GenericListAdapter<ItemWithCategory>(R.layout.item_skill,
                        bind = { item, holder, _ ->
                            with(holder.itemView) {
                                findViewById<TextView>(R.id.tv_title).text = item.name
                                viewLifecycleOwner.lifecycleScope.launch {
                                    @Suppress("BlockingMethodInNonBlockingContext")
                                    findViewById<ImageView>(R.id.iv_item_image).setImageBitmap(
                                        Utils.getBitmapFromAssets(
                                            item.imageName,
                                            requireActivity().application
                                        )
                                    )
                                }
                                setOnClickListener {
                                    navigateToItemFragment(item)
                                }
                            }

                        }) {}.apply {}
                adapter = rvAdapter
            }
        }
    }

    private fun navigateToItemFragment(item: ItemWithCategory) {
        findNavController().navigate(
            ItemListFragmentDirections.actionNavigateFromItemListFragmentToItemFragment(
                viewModel.gameId,
                item
            )
        )
    }

    private fun updateList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getSkillListUseCase(
                viewModel.category.value?.category?.id ?: return@launch
            ).collect() {
                rvAdapter.submitList(it)
            }

        }
    }


    override fun handleBackAction() {
        setFragmentResult(
            CATEGORY,
            bundleOf(Pair("category", viewModel.category.value), Pair("game_id", viewModel.gameId))
        )
        super.handleBackAction()
    }

    private fun setToolBarTitle(name: String?) {
        with(toolbar) {
            title = name
        }
    }
}