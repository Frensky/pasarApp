package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class GetTopItemUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(): List<Item>? {
        val response = repository.getTopItem()
        return response
    }
}