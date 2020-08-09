package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemByKeyWordUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(keyWord : String): List<Item> {
        val response = repository.searchItemByKeyWord(keyWord)
        return response
    }
}