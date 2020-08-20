package com.adut.pasar.domain.repository

import com.adut.pasar.domain.model.Item
import java.io.File

interface SyncronRepository {
    suspend fun importItemData(file: File) : Boolean
    suspend fun exportItemData(exportPath: String,itemData:ArrayList<Item>): File?
}