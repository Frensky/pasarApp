package com.adut.pasar.data.di

import android.content.Context
import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.data.local.preference.Preferences
import com.adut.pasar.data.repository.ItemDataRepository
import com.adut.pasar.data.repository.SettingDataRepository
import com.adut.pasar.data.repository.SyncronDataRepository
import com.adut.pasar.domain.repository.ItemRepository
import com.adut.pasar.domain.repository.SettingRepository
import com.adut.pasar.domain.repository.SyncronRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class RepoModule {
    @Singleton
    @Provides
    fun provideItemRepository(itemDao: ItemDAO): ItemRepository {
        return ItemDataRepository(itemDao)
    }

    @Singleton
    @Provides
    fun provideSettingRepo(pref: Preferences): SettingRepository {
        return SettingDataRepository(pref)
    }

    @Singleton
    @Provides
    fun provideSyncronRepo(itemDao: ItemDAO): SyncronRepository {
        return SyncronDataRepository(itemDao)
    }

}