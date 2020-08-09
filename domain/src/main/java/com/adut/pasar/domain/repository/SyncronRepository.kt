package com.adut.pasar.domain.repository

import com.adut.pasar.domain.model.Item
import java.io.File

interface SyncronRepository {
    suspend fun importItemData(file: File)
    suspend fun exportItemData(): File
}