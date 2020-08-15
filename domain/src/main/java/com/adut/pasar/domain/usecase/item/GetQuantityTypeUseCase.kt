package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class GetQuantityTypeUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(): List<String> {
        val response = repository.getItemQuantityType()
        return response
    }
}