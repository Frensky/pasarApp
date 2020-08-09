package com.adut.pasar.domain.usecase.syncron

import com.adut.pasar.domain.model.SyncronState
import com.adut.pasar.domain.repository.SyncronRepository
import java.io.File
import javax.inject.Inject

class ImportDataUseCase @Inject constructor(
    private val repository: SyncronRepository
) {
    suspend fun execute(file : File) :  SyncronState {
        val isSuccess = repository.importItemData(file)
        if(isSuccess){
            return SyncronState(true,"Data barang sukses di simpan")
        }
        else{
            return SyncronState(false,"Data barang GAGAL di simpan")
        }
    }
}