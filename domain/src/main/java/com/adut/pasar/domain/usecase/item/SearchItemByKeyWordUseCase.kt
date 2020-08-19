package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class SearchItemByKeyWordUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(keyWord : String): List<Item> {
        val response = repository.searchItemByKeyWord(keyWord,false)
        return response
    }

    suspend fun executeFavorite(keyWord : String): List<Item> {
        val response = repository.searchItemByKeyWord(keyWord,true)
        return response
    }
}