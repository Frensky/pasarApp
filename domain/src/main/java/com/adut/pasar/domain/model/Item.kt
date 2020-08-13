package com.adut.pasar.domain.model
data class Item(
    val id : Long = -1,
    val isBookmarked : Boolean = false,
    val title : String = "",
    val qty : Int = 0,
    val qtyType : String = "",
    val jual : Long = 0,
    val beli : Long = 0,
    val distributor : String = "",
    val barCodeId : String = ""
)