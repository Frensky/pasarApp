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

    override suspend fun searchItemByKeyWord(key: String, isFavorite: Boolean): List<Item> {
        var response = itemDAO.getItemsByKeyWord(key)
        if(isFavorite){
          response = itemDAO.getFavItemsByKeyWord(key)
        }
        val result = response.map { it.mapToEntity() }
        return result
    }

    override suspend fun searchTitleByKeyWord(key: String, isFavorite: Boolean): List<String> {
        var response = itemDAO.getTitleByKeyWord(key)
        if(isFavorite){
            response = itemDAO.getTitleByKeyWord(key)
        }
        return response
    }

    override suspend fun getFavoriteItem(): List<Item> {
        val response = itemDAO.getFavoriteItems()
        val result = response.map { it.mapToEntity() }
        return result
    }

    override suspend fun getItemQuantityType(): List<String> {
        val response = itemDAO.getQuantityType()
        return response
    }

    override suspend fun getItemByBarcodeId(Id: String): Item? {
        val response = itemDAO.getItemsByBarCode(Id)
        var result = response.firstOrNull()?.mapToEntity()
        return result
    }

    override suspend fun getItemById(Id: Long): Item? {
        val response = itemDAO.getItemById(Id)
        var result = response?.mapToEntity()
        return result
    }

    override suspend fun saveItemData(item: Item) {
        var ids = item.id

        if(ids < 0){
            ids = itemDAO.getIdDesc().firstOrNull() ?: 0
        }

        val entity = ItemEntity(
            id = ids,title = item.title,quantity = item.qty,quantityType = item.qtyType,buyPrice = item.beli, sellPrice = item.jual, barCodeId = item.barCodeId,isBookMark = item.isBookmarked,distributor = item.distributor
        )

        itemDAO.saveItem(entity)
    }

    override suspend fun updateItem(item: Item) {
        val entity = ItemEntity(
            id = item.id,title = item.title,quantity = item.qty,quantityType = item.qtyType,buyPrice = item.beli, sellPrice = item.jual, barCodeId = item.barCodeId,isBookMark = item.isBookmarked,distributor = item.distributor
        )
        itemDAO.updateItem(entity)
    }

    override suspend fun deleteItem(item: Item) {
        itemDAO.deleteByItemID(item.id)
    }

    override suspend fun clearData() {
        itemDAO.deleteAll()
    }
}