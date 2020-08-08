package com.adut.pasar.data.di

import android.content.Context
import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.data.repository.ItemDataRepository
import com.adut.pasar.domain.repository.ItemRepository
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

}