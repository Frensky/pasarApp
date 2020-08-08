package com.adut.pasar.domain.repository

import com.adut.pasar.domain.model.Item

interface ItemRepository {
    suspend fun getAllItem(): List<Item>
    suspend fun getItemById(Id: Int): Item?
    suspend fun getItemByKeyWord(key:String): List<Item>
    suspend fun saveItemData(item:Item)
    suspend fun updateItem(item:Item)
    suspend fun deleteItem(item:Item)
}