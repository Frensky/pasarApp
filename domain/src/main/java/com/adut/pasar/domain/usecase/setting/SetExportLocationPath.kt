package com.adut.pasar.domain.usecase.setting

import com.adut.pasar.domain.repository.SettingRepository
import javax.inject.Inject

class SetExportLocationPath @Inject constructor(
    private val repository: SettingRepository
) {
    suspend fun execute(path : String) {
        repository.setCSVExportLocation(path)
    }
}