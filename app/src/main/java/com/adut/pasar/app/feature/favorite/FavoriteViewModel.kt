package com.adut.pasar.app.feature.favorite

import androidx.lifecycle.ViewModel
import com.adut.pasar.domain.usecase.item.GetFavoriteItemUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val getFavoriteItemUseCase: GetFavoriteItemUseCase
) : ViewModel() {

}