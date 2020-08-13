package com.adut.pasar.data.repository

import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.data.model.ItemEntity
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
        val response = itemDAO.getFavoriteItems()
        val result = response.map { it.mapToEntity() }
        return result
    }

    override suspend fun getItemQuantityType(): List<String> {
        TODO("Not yet implemented")
        val response = itemDAO.getQuantityType()
        return response
    }

    override suspend fun getItemByBarcodeId(Id: String): Item? {
        TODO("Not yet implemented")
        val response = itemDAO.getItemsByBarCode(Id)
        var result = response.first()?.mapToEntity()
        return result
    }

    override suspend fun saveItemData(item: Item) {
        TODO("Not yet implemented")
        val entity = ItemEntity(
            id = item.id,title = item.title,quantity = item.qty,quantityType = item.qtyType,buyPrice = item.beli, sellPrice = item.jual, barCodeId = item.barCodeId,isBookMark = item.isBookmarked,distributor = item.distributor
        )
        itemDAO.saveItem(entity)

    }

    override suspend fun updateItem(item: Item) {
        TODO("Not yet implemented")
        val entity = ItemEntity(
            id = item.id,title = item.title,quantity = item.qty,quantityType = item.qtyType,buyPrice = item.beli, sellPrice = item.jual, barCodeId = item.barCodeId,isBookMark = item.isBookmarked,distributor = item.distributor
        )
        itemDAO.updateItem(entity)
    }

    override suspend fun deleteItem(item: Item) {
        TODO("Not yet implemented")
        itemDAO.deleteByItemID(item.id)
    }

    override suspend fun clearData() {
        TODO("Not yet implemented")
        itemDAO.deleteAll()
    }
}