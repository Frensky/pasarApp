package com.adut.pasar.app.feature.dashboard

import androidx.lifecycle.ViewModel
import com.adut.pasar.domain.usecase.item.GetAllItemUseCase
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val getAllItemUseCase: GetAllItemUseCase
) : ViewModel() {

}