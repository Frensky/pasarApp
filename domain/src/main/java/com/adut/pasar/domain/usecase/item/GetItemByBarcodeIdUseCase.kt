package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class GetItemByBarcodeIdUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute(id : String): Item? {
        val response = repository.getItemByBarcodeId(id)
        return response
    }
}