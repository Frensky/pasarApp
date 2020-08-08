package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class GetAllItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(): ArrayList<Item>? {
        val response = repository.getAllItem()
        //val result = filterContactItem(response)
        //return result
        val result = ArrayList<Item>()
        result.addAll(response)
        return result
    }
}