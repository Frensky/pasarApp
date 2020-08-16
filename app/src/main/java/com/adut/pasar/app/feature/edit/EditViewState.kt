package com.adut.pasar.app.feature.edit

import com.adut.pasar.domain.model.Item

data class EditViewState(
    val type : String,
    val items : Item
)