package com.adut.pasar.data.local.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


object Migration {
    // Don't forget to change the version in AppDatabase after you add MIGRATION
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //clear all misalkan
        }
    }
}