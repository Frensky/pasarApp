package com.adut.pasar.domain.usecase.setting

import com.adut.pasar.domain.repository.SettingRepository
import javax.inject.Inject

class SetJualShownUseCase @Inject constructor(
    private val repository: SettingRepository
) {
    suspend fun execute(isShown : Boolean) {
        repository.updateJualShown(isShown)
    }
}