package com.omfgdevelop.falloutfullinfo.data

import com.omfgdevelop.falloutfullinfo.domian.dao.SkillDao
import com.omfgdevelop.falloutfullinfo.domian.entity.SkillWithCategory
import com.omfgdevelop.falloutfullinfo.domian.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first

object SkillRepositoryImpl : SkillRepository {

    private var skillRepository: SkillRepository? = null

    private lateinit var dao: SkillDao

    fun create(dao: SkillDao): SkillRepository {
        this.dao = dao
        if (this.skillRepository == null) {
            skillRepository = SkillRepositoryImpl
        }
        return skillRepository!!
    }
    override suspend fun getSKillList(categoryId: Long): Flow<List<SkillWithCategory>> {
        return dao.getSkillListByCategoryId(categoryId)
    }
}