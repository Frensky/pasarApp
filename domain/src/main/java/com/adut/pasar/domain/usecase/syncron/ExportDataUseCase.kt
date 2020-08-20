package com.adut.pasar.domain.usecase.syncron

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import com.adut.pasar.domain.repository.SettingRepository
import com.adut.pasar.domain.repository.SyncronRepository
import java.io.File
import javax.inject.Inject

class ExportDataUseCase @Inject constructor(
    private val repository: SyncronRepository,
    private val productRepo: ItemRepository,
    private val settingRepo: SettingRepository
) {
    suspend fun execute() : File? {
        val item = productRepo.getTopItem()
        val exportPath = settingRepo.getCSVExportLocation()

        if(!exportPath.isNullOrEmpty() && !item.isEmpty()){
            val datas = ArrayList<Item>()
            datas.addAll(item)
            return repository.exportItemData(exportPath, datas)
        }
        else{
            return null
        }

    }
}