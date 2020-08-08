package com.adut.pasar.domain.model

data class Item(
    val id: Long = -1,
    val title:String = "",
    val jual:Long = 0,
    val beli:Long = 0
)