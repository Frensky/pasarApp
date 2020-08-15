package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class ItemDAOUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun clearData(){
        repository.clearData()
    }

    suspend fun saveItemData(item:Item){
        repository.saveItemData(item)
    }

    suspend fun updateItem(item:Item){
        repository.updateItem(item)
    }

    suspend fun deleteItem(item:Item){
        repository.deleteItem(item)
    }
}