package com.adut.pasar.data.model

interface ModelEntity<T> {
    fun mapToEntity(): T
}

fun <T> List<ModelEntity<T>>.mapToEntityList(): List<T> {
    val list = mutableListOf<T>()
    for (item in this) {
        list.add(item.mapToEntity())
    }
    return list
}