package com.adut.pasar.data.di

import android.content.Context
import androidx.room.Room
import com.adut.pasar.data.local.db.AppDatabase
import com.adut.pasar.data.local.db.Migration
import com.adut.pasar.data.local.db.dao.ItemDAO
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
open class DatabaseModule {

    companion object {
        const val QUALIFIER_APP_DATABASE = "APP_DATABASE"
    }

    @Singleton
    @Provides
    @Named(QUALIFIER_APP_DATABASE)
    fun provideAppDatabase(context: Context): AppDatabase {
        try {
            return Room.databaseBuilder(context, AppDatabase::class.java, "pasar_db")
                .allowMainThreadQueries()
                .addMigrations(Migration.MIGRATION_1_2)
                .build()
        } catch (e: Exception) {
            throw e
        }
    }

    @Singleton
    @Provides
    fun provideItemrDao(@Named(QUALIFIER_APP_DATABASE) database: AppDatabase): ItemDAO {
        return database.itemDao()
    }


}