package com.omfgdevelop.falloutfullinfo.utils

import android.app.Application
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

object Utils {

    @Throws(IOException::class)
    suspend fun getBitmapFromAssets(fileName: String?, app: Application): Bitmap? {
        try {
            val assetManager: AssetManager = app.assets
            val istr: InputStream = assetManager.open(fileName?.trim() ?: "")
            val bitmap = BitmapFactory.decodeStream(istr)
            withContext(Dispatchers.IO) {
                istr.close()
            }
            return bitmap
        } catch (e: Exception) {
            e.printStackTrace();
        }
        return null;

    }
}