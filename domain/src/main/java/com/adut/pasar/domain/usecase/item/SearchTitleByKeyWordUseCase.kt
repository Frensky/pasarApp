package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class SearchTitleByKeyWordUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(keyWord : String): List<String> {
        val response = repository.searchTitleByKeyWord(keyWord,false)
        return response
    }

    suspend fun executeFavorite(keyWord : String): List<String> {
        val response = repository.searchTitleByKeyWord(keyWord,true)
        return response
    }
}