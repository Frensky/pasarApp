package com.adut.pasar.app.util

import android.util.Log
import com.adut.pasar.app.BuildConfig

object LogUtil{
    fun log(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.i("Information", message)
        }
    }

    fun logError(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e("Error", message)
        }
    }
}