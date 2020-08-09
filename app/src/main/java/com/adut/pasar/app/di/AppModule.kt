package com.adut.pasar.app.di

import android.content.Context
import com.adut.pasar.app.MyApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    @Singleton
    fun providesContext(): Context = context
}