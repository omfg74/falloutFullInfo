package com.omfgdevelop.falloutfullinfo.data.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.omfgdevelop.falloutfullinfo.domian.entity.Game
import com.omfgdevelop.falloutfullinfo.domian.entity.GameDao

@Database(entities = arrayOf(Game::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao

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