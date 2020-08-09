package com.adut.pasar.data.local.preference

interface Preferences {
    fun getShouldJualShown() : Boolean
    fun setJualShown(shown : Boolean)
    fun clearAllCache()
}