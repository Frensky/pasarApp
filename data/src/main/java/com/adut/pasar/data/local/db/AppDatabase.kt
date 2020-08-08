package com.adut.pasar.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.data.model.ItemEntity

@Database(
    entities = [
        ItemEntity::class
    ],
    version = 1
)

@TypeConverters(DataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDAO
}