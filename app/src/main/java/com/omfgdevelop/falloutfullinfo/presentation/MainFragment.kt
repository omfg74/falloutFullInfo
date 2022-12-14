package com.omfgdevelop.falloutfullinfo.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentMainBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.Game
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.MainFragmentViewModel
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.MainViewModelFactory
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw java.lang.RuntimeException("binding is null")

    private lateinit var recyclerView: RecyclerView


    private val viewModelFactory by lazy {
        MainViewModelFactory(requireActivity().application)
    }

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainFragmentViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setViews()
    }

    private fun setViews() {
        with(binding) {
            recyclerView = rvContent
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = object : GenericListAdapter<Game>(
                    android.R.layout.simple_list_item_2,
                    bind = { item, holder, _ ->
                        run {
                            with(holder.itemView) {
                                findViewById<TextView>(android.R.id.text1).text = item.name
                                findViewById<TextView>(android.R.id.text1).setTextColor(ContextCompat.getColor(requireContext(),
                                    R.color.monitor_green_dark))
                            }
                            holder.itemView.setOnClickListener(object : OnClickListener {
                                override fun onClick(p0: View?) {
                                    Toast.makeText(
                                        requireContext(),
                                        "$item.name clicked",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            })
                        }
                    }) {}.apply {
                    lifecycle.coroutineScope.launch {
                        viewModel?.getAllGames()?.collect() {
                            submitList(it)
                        }
//                        submitList(listOf(Game(1,"name")))
                    }
                }
            }
        }
    }
}