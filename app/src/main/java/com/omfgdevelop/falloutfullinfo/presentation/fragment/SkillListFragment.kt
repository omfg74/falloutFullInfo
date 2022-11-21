package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentSkillListBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.entity.SkillEntity
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.SkillListViewModel

class SkillListFragment :
    BaseToolbarFragment<SkillListViewModel, FragmentSkillListBinding>(SkillListViewModel::class.java) {

    private lateinit var recyclerView: RecyclerView

    private lateinit var rvAdapter: GenericListAdapter<SkillEntity>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setViews() {
        with(binding) {
            recyclerView = rvSkillList
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                rvAdapter = object : GenericListAdapter<SkillEntity>(
                    R.layout.item_skill,
                    bind = { item, holder, itemCount -> }) {}.apply { }
            }
        }
    }
}