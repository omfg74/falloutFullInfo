package com.omfgdevelop.falloutfullinfo.domian

import androidx.room.TypeConverter
import com.omfgdevelop.falloutfullinfo.domian.entity.ChildType


class ChildTypeTypeConverter {

    @TypeConverter
    fun toChildType(value: Int): ChildType {
        return ChildType.fromInt(value)
    }

    @TypeConverter
    fun toInt(type: ChildType): Int {
        return type.value
    }
}