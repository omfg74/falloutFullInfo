package com.omfgdevelop.falloutfullinfo.presentation.fragment

import android.annotation.SuppressLint
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omfgdevelop.falloutfullinfo.R
import com.omfgdevelop.falloutfullinfo.databinding.FragmentSkillListBinding
import com.omfgdevelop.falloutfullinfo.domian.entity.SkillWithCategory
import com.omfgdevelop.falloutfullinfo.presentation.view.adapter.GenericListAdapter
import com.omfgdevelop.falloutfullinfo.presentation.viewModel.SkillListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream


class SkillListFragment :
    BaseToolbarFragment<SkillListViewModel, FragmentSkillListBinding>(SkillListViewModel::class.java) {

    companion object {
        const val CATEGORY: String = "CATEGORY"
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var rvAdapter: GenericListAdapter<SkillWithCategory>

    private val args by navArgs<SkillListFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSkillListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.category = args.category
        viewModel.gameId = args.gameId
        super.onViewCreated(view, savedInstanceState)
        setToolBarTitle(viewModel.category.category.name)
    }

    @SuppressLint("ResourceType")
    override fun setViews() {
        with(binding) {
            recyclerView = rvSkillList
            with(recyclerView) {
                layoutManager = LinearLayoutManager(requireContext())
                rvAdapter = object :
                    GenericListAdapter<SkillWithCategory>(R.layout.item_skill,
                        bind = { item, holder, _ ->
                            with(holder.itemView) {
                                findViewById<TextView>(R.id.tv_title).text = item.name
                                viewLifecycleOwner.lifecycleScope.launch {
                                    @Suppress("BlockingMethodInNonBlockingContext")
                                    findViewById<ImageView>(R.id.iv_skill_image).setImageBitmap(
                                        getBitmapFromAssets(item.imageName)
                                    )
                                }
                            }
                        }) {}.apply {
                    viewLifecycleOwner.lifecycleScope.launch {
                        viewModel?.getSkillListUseCase?.let {
                            it(
                                viewModel?.category?.category?.id ?: return@launch
                            )
                        }
                            ?.collect() {
                                submitList(it)
                            }

                    }
                }
                adapter = rvAdapter
            }
        }
    }

    @Throws(IOException::class)
    suspend fun getBitmapFromAssets(fileName: String?): Bitmap? {
        val assetManager: AssetManager = requireActivity().assets
        val istr: InputStream = assetManager.open(fileName ?: "")
        val bitmap = BitmapFactory.decodeStream(istr)
        withContext(Dispatchers.IO) {
            istr.close()
        }
        return bitmap
    }

    override fun handleBackAction() {
        setFragmentResult(
            CATEGORY,
            bundleOf(Pair("category", viewModel.category), Pair("game_id", viewModel.gameId))
        )
        super.handleBackAction()
    }

    private fun setToolBarTitle(name: String?) {
        with(toolbar) {
            title = name
        }
    }
}