package com.adut.pasar.domain.model
data class Item(
    var id : Long = -1,
    var isBookmarked : Boolean = false,
    var title : String = "",
    var qty : Int = 0,
    var qtyType : String = "",
    var jual : Long = 0,
    var beli : Long = 0,
    var distributor : String = "",
    var barCodeId : String = ""
)