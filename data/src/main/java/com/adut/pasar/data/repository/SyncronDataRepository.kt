package com.adut.pasar.data.repository

import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.domain.repository.SyncronRepository
import java.io.File
import javax.inject.Inject

class SyncronDataRepository @Inject constructor(
    private val itemDAO: ItemDAO
) : SyncronRepository {

    override suspend fun importItemData(file: File) {
        TODO("Not yet implemented")
    }

    override suspend fun exportItemData(): File {
        TODO("Not yet implemented")
    }

}