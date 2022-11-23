package com.omfgdevelop.falloutfullinfo.presentation.viewModel

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.omfgdevelop.falloutfullinfo.App
import com.omfgdevelop.falloutfullinfo.data.ItemRepositoryImpl
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity
import com.omfgdevelop.falloutfullinfo.domian.repository.ItemRepository
import com.omfgdevelop.falloutfullinfo.domian.usecases.GetItemUseCase
import com.omfgdevelop.falloutfullinfo.utils.Utils
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class ItemFragmentViewModel(private val app: Application) : AndroidViewModel(app) {

    var gameId by Delegates.notNull<Long>()

    private val itemRepository: ItemRepository =
        ItemRepositoryImpl.create((app as App).dataBase.ItemDao())

    private val getItemUseCase: GetItemUseCase by lazy { GetItemUseCase(itemRepository) }

    val title = ObservableField<String>()

    val imageResource = ObservableField<Drawable>()

    val description = ObservableField<String>()

    lateinit var item: ItemEntity
    var itemId: Long = 0

    fun loadItem() {
        viewModelScope.launch {
            getItemUseCase(itemId).collect() {
                item = it
                imageResource.set(
                    Utils.getBitmapFromAssets(it.imageName, app)?.toDrawable(app.resources)
                )
                title.set(it.name)
                description.set(it.description)
            }
        }
    }
}
