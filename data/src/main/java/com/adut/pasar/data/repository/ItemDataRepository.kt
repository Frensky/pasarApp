package com.adut.pasar.data.repository

import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class ItemDataRepository @Inject constructor(
    private val itemDAO: ItemDAO
) : ItemRepository {

    override suspend fun getAllItem(): List<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun getItemById(Id: Int): Item? {
        TODO("Not yet implemented")
    }

    override suspend fun getItemByKeyWord(key: String): List<Item> {
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
}