package com.adut.pasar.domain.usecase.item

import com.adut.pasar.domain.repository.ItemRepository
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute() {
        //logging something in here using LogUtil to test etc etc
        val response = repository.getTopItem()

    }
}