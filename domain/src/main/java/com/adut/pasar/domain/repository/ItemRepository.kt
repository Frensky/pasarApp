package com.adut.pasar.domain.repository

import com.adut.pasar.domain.model.Item

interface ItemRepository {
    suspend fun getTopItem(): List<Item>
    suspend fun getFavoriteItem(): List<Item>
    suspend fun getItemById(Id: Long): Item?

    suspend fun getItemByBarcodeId(Id: String): Item?
    suspend fun searchItemByKeyWord(key:String,isFavorite:Boolean): List<Item>
    suspend fun searchTitleByKeyWord(key:String,isFavorite:Boolean): List<String>

    suspend fun getItemQuantityType(): List<String>

    suspend fun saveItemData(item:Item)
    suspend fun updateItem(item:Item)
    suspend fun deleteItem(item:Item)

    suspend fun clearData()
}