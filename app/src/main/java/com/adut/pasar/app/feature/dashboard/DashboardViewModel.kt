package com.adut.pasar.app.feature.dashboard

import androidx.lifecycle.ViewModel
import com.adut.pasar.domain.usecase.item.GetTopItemUseCase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getAllItemUseCase: GetTopItemUseCase
) : ViewModel() {

}