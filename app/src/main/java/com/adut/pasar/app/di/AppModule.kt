package com.adut.pasar.app.di

import android.content.Context
import com.adut.pasar.app.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return MyApplication.mInstance
    }
}