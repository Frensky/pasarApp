package com.adut.pasar.app.util

import android.Manifest

class AppConstant {
    companion object{
        val FILE_PERMISION = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        val CAMERA_PERMISION = arrayOf(
            Manifest.permission.CAMERA
        )

        val WORD_LIMIT = 3
    }
}