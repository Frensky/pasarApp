package com.adut.pasar.data.repository

import com.adut.pasar.data.local.preference.Preferences
import com.adut.pasar.domain.repository.SettingRepository
import javax.inject.Inject

class SettingDataRepository @Inject constructor(
    private val prefs:Preferences
) : SettingRepository {
    override suspend fun updateJualShown(isShown: Boolean) {
        return prefs.setJualShown(isShown)
    }

    override suspend fun isJualShown(): Boolean {
        return  prefs.getShouldJualShown()
    }
}