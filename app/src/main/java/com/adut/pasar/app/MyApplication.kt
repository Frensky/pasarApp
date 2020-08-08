package com.adut.pasar.app

import android.app.Application
import com.adut.pasar.app.di.AppComponent
import com.adut.pasar.app.di.DaggerAppComponent

class MyApplication: Application() {



    companion object {
        @JvmStatic lateinit var mInstance : MyApplication
        @JvmStatic lateinit var appComponent: AppComponent
        @Synchronized
        fun getInstance(): MyApplication {
            return MyApplication.mInstance
        }
    }

    init {
        mInstance = this
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
        appComponent.inject(this)
    }


}