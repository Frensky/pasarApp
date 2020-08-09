package com.adut.pasar.domain.util

import android.util.Log
import com.adut.pasar.domain.BuildConfig

object LogUtil{
    fun log(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.i("Pasar App", message)
        }
    }

    fun logError(message: String?) {
        if (BuildConfig.DEBUG) {
            Log.e("Pasar App", message)
        }
    }

    fun logError(error: Throwable?) {
        if (BuildConfig.DEBUG) {
            Log.e("Pasar App", "exception", error);
        }
    }
}