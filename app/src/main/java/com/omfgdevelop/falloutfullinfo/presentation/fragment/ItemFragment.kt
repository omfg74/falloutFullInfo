package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.omfgdevelop.falloutfullinfo.databinding.FragmentItemBinding
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.ItemFragmentViewModel

class ItemFragment : BaseToolbarFragment<ItemFragmentViewModel, FragmentItemBinding>(
    ItemFragmentViewModel::class.java
) {
    companion object {
        const val ITEM: String = "ITEM"
    }

    private val args by navArgs<ItemFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.itemId = args.item.id
        viewModel.gameId = args.gameId
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadItem()
        setToolBarTitle(viewModel.title.get())
    }

    override fun setViews() {
    }

    private fun setToolBarTitle(name: String?) {
        with(toolbar) {
            title = name
        }
    }


    override fun handleBackAction() {
        setFragmentResult(
            ItemListFragment.CATEGORY,
            bundleOf(Pair("item", viewModel.item), Pair("game_id", viewModel.gameId))
        )
        super.handleBackAction()
    }
}