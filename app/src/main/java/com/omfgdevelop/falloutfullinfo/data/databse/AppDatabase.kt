package com.omfgdevelop.falloutfullinfo.data.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omfgdevelop.falloutfullinfo.domian.ChildTypeTypeConverter
import com.omfgdevelop.falloutfullinfo.domian.dao.CategoryDao
import com.omfgdevelop.falloutfullinfo.domian.dao.GameDao
import com.omfgdevelop.falloutfullinfo.domian.dao.ItemDao
import com.omfgdevelop.falloutfullinfo.domian.entity.CategoryEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.CategoryToGame
import com.omfgdevelop.falloutfullinfo.domian.entity.GameEntity
import com.omfgdevelop.falloutfullinfo.domian.entity.ItemEntity

@Database(
    entities = arrayOf(
        GameEntity::class,
        CategoryEntity::class,
        CategoryToGame::class,
        ItemEntity::class
    ), version = 1
)
@TypeConverters(value = [ChildTypeTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun ItemDao(): ItemDao
    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "ffi"
                ).createFromAsset("database/ffi.db").build()

                instance
            }
        }
    }
}