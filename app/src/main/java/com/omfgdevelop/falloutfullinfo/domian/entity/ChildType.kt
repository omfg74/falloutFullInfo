package com.omfgdevelop.falloutfullinfo.domian.entity

enum class ChildType(val value: Int) {
    CATEGORY(0), SKILL_DESCRIPTION(1), ARTICLE(2),ITEM(3);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
