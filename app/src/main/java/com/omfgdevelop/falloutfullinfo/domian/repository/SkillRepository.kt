package com.omfgdevelop.falloutfullinfo.domian.repository

import com.omfgdevelop.falloutfullinfo.domian.entity.SkillWithCategory
import kotlinx.coroutines.flow.Flow

interface SkillRepository {
    suspend fun getSKillList(categoryId: Long): Flow<List<SkillWithCategory>>
}