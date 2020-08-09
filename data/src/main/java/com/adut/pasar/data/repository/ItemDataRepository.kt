package com.adut.pasar.data.repository

import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class ItemDataRepository @Inject constructor(
    private val itemDAO: ItemDAO
) : ItemRepository {

    override suspend fun getTopItem(): List<Item> {
        val response = itemDAO.getTopItems()
        val result = response.map { it.mapToEntity() }
        return result
    }

    override suspend fun searchItemByKeyWord(key: String): List<Item> {
        val response = itemDAO.getItemsByKeyWord(key)
        val result = response.map { it.mapToEntity() }
        return result
    }

    override suspend fun searchTitleByKeyWord(key: String): List<String> {
        val response = itemDAO.getTitleByKeyWord(key)
        return response
    }

    override suspend fun getFavoriteItem(): List<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemQuantityType(): List<String> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemByBarcodeId(Id: String): Item? {
        TODO("Not yet implemented")
    }

    override suspend fun saveItemData(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(item: Item) {
        TODO("Not yet implemented")
    }

    override suspend fun clearData() {
        TODO("Not yet implemented")
    }
}