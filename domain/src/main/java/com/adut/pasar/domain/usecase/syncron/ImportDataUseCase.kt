package com.adut.pasar.domain.usecase.syncron

import com.adut.pasar.domain.repository.SyncronRepository
import java.io.File
import javax.inject.Inject

class ImportDataUseCase @Inject constructor(
    private val repository: SyncronRepository
) {
    suspend fun execute(file : File)  {
        repository.importItemData(file)
    }
}