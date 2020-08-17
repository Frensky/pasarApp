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
){
    fun mapToCSVString(iterator:Int?) : String {
        val counter : String = iterator?.toString() ?: id.toString()
        val bookmarkFlag = if(isBookmarked) "Ya" else "Tidak"
        return counter+","+title+","+qty+","+qtyType+","+beli+","+jual+",,"+distributor+",,"+barCodeId+","+bookmarkFlag+"\n"
    }
}