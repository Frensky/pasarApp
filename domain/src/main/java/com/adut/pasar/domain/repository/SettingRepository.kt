package com.adut.pasar.domain.repository

import com.adut.pasar.domain.model.Item

interface SettingRepository {
    suspend fun isJualShown(): Boolean
    suspend fun updateJualShown(isShown : Boolean)
}