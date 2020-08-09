package com.adut.pasar.domain.usecase.setting

import com.adut.pasar.domain.repository.SettingRepository
import javax.inject.Inject

class IsJualShownUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend fun execute(): Boolean {
        val response = repository.isJualShown()
        return response
    }
}