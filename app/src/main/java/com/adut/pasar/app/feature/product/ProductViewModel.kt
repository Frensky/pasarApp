package com.adut.pasar.app.feature.product

import androidx.lifecycle.ViewModel
import com.adut.pasar.domain.usecase.item.GetTopItemUseCase
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    private val getAllItemUseCase: GetTopItemUseCase
) : ViewModel() {

}