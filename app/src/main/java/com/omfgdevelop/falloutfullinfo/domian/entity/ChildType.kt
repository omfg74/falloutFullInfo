package com.omfgdevelop.falloutfullinfo.domian.entity

enum class ChildType(val value: Int) {
    CATEGORY(0), ITEM(1), ARTICLE(2);

    companion object {
        fun fromInt(value: Int) = values().first { it.value == value }
    }
}
