package com.adut.pasar.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class DataTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun itemDataToJsonObject(data: String?): JsonObject {
        return if (data != null) {
            val parser = JsonParser()
            parser.parse(data).asJsonObject
        } else {
            JsonObject()
        }
    }

    @TypeConverter
    fun itemDataJsonToString(json: JsonObject?): String {
        return json?.toString() ?: ""
    }
}