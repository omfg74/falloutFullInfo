package com.omfgdevelop.falloutfullinfo.domian.usecases

import com.omfgdevelop.falloutfullinfo.domian.entity.SkillWithCategory
import com.omfgdevelop.falloutfullinfo.domian.repository.SkillRepository
import kotlinx.coroutines.flow.Flow

class GetSkillListUseCase(private val skillRepository: SkillRepository) {

    suspend operator fun invoke(categoryId: Long): Flow<List<SkillWithCategory>> {
        return skillRepository.getSKillList(categoryId)
    }
}