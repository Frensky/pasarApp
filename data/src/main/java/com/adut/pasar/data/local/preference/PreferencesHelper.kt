package com.adut.pasar.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesHelper @Inject constructor(
    private val context: Context
) : Preferences {

    companion object {
        const val PREF_GENERAL_NAME = "PasarAppPrefFile"
        const val PREF_JUAL_SHOWN = "pref_jual_shown"
        const val EXPORT_CSV_LOC = "pref_export_CSV_LOC"
    }

    private val generalPreferences: SharedPreferences

    //This does not handle the Set<String>
    fun <T> SharedPreferences.put(key: String, input: T) {
        when (input) {
            is Boolean -> {
                this.edit().putBoolean(key, input).apply()
            }
            is Float -> {
                this.edit().putFloat(key, input).apply()
            }
            is Int -> {
                this.edit().putInt(key, input).apply()
            }
            is Long -> {
                this.edit().putLong(key, input).apply()
            }
            is String -> {
                this.edit().putString(key, input).apply()
            }
        }
    }

    init {
        generalPreferences = context.getSharedPreferences(PREF_GENERAL_NAME, Context.MODE_PRIVATE)
    }

    override fun getShouldJualShown(): Boolean {
        return generalPreferences.getBoolean(PREF_JUAL_SHOWN, false)
    }

    override fun setJualShown(shown: Boolean) {
        generalPreferences.put(PREF_JUAL_SHOWN, shown)
    }

    override fun clearAllCache() {
        generalPreferences.edit().clear().commit()
    }

    override fun getExportCSVLocation(): String? {
        return generalPreferences.getString(EXPORT_CSV_LOC,null)
    }

    override fun setExportCSVLocation(path: String?) {
        generalPreferences.put(EXPORT_CSV_LOC,path)
    }


}