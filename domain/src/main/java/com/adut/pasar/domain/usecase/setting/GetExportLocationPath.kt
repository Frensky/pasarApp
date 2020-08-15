package com.adut.pasar.domain.usecase.setting

import com.adut.pasar.domain.repository.SettingRepository
import javax.inject.Inject

class GetExportLocationPath @Inject constructor(
    private val repository: SettingRepository
) {
    suspend fun execute(): String? {
        val response = repository.getCSVExportLocation()
        return response
    }
}