package com.omfgdevelop.falloutfullinfo.data

import com.omfgdevelop.falloutfullinfo.domian.dao.CategoryDao
import com.omfgdevelop.falloutfullinfo.domian.entity.Category
import com.omfgdevelop.falloutfullinfo.domian.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow

object CategoryRepositoryImpl : CategoryRepository {
    private lateinit var dao: CategoryDao

    private var repository: CategoryRepository? = null
    fun create(dao: CategoryDao): CategoryRepository {
        this.dao = dao
        if (this.repository == null) {
            this.repository = CategoryRepositoryImpl
        }
        return repository!!
    }

    override suspend fun getCategoryListByParentId(
        parentId: Long,
        gameId: Long
    ): Flow<List<Category>> {
        return dao.getChildCategoryList(parentId, gameId)
    }

    override suspend fun getCategoryById(id: Long, gameId: Long): Flow<Category> {
        return dao.getCategoryById(id, gameId)
    }


}
