package com.omfgdevelop.falloutfullinfo

import android.app.Application
import com.omfgdevelop.falloutfullinfo.data.databse.AppDatabase

class App : Application() {
    val dataBase: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}