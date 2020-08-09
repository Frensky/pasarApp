package com.adut.pasar.data.repository

import com.adut.pasar.data.local.db.dao.ItemDAO
import com.adut.pasar.data.model.ItemEntity
import com.adut.pasar.domain.repository.SyncronRepository
import com.adut.pasar.domain.util.LogUtil
import com.opencsv.CSVReader
import java.io.File
import java.io.FileReader
import javax.inject.Inject


class SyncronDataRepository @Inject constructor(
    private val itemDAO: ItemDAO
) : SyncronRepository {

    companion object {
        const val ID_INDEX = 0
        const val TITLE_INDEX = 1
        const val QTY_INDEX = 2
        const val QTY_TYPE_INDEX = 3
        const val SELL_PRICE_INDEX = 4
        const val BUY_PRICE_INDEX = 5
        const val NOTES_INDEX = 6
        const val SUPLIER_NAME_INDEX = 7
        const val SUPLIER_CONTACT_INDEX = 8
        const val BARCODE_INDEX = 9
        const val BOKMARK_INDEX = 10
    }

    override suspend fun importItemData(file: File) : Boolean{
        if(file.name.contains(".csv")){
            itemDAO.deleteAll()
            try {
                val reader = CSVReader(FileReader(file))
                var nextLine: Array<String>? = null
                var lines = 0
                while (reader.readNext().also { nextLine = it } != null) {
                    if(nextLine.isNullOrEmpty()){
                        break
                    }
                    else if(nextLine!![0].isNullOrEmpty()){
                        break
                    }
                    else{
                        if(lines != 0){
                            val items : ItemEntity = inputItemData(nextLine!!)
                            itemDAO.saveItem(items)
                        }
                        lines = lines + 1
                    }
                }
                return true
            } catch (e: Exception) {
                LogUtil.logError(e)
            }
        }
        return false
    }

    private fun inputItemData(data : Array<String>):ItemEntity{
        val result = ItemEntity()

        var indexz = 0
        for(it in data!!){
            when(indexz){
                TITLE_INDEX -> {
                    result.title = it?.trim()
                }
                QTY_INDEX -> {
                    result.quantity = formatToNumber(it)
                }
                QTY_TYPE_INDEX -> {
                    var type = it?.trim()
                    if(it.isNullOrEmpty()){
                        type = "Unit"
                    }
                    result.quantityType = type
                }
                SELL_PRICE_INDEX -> {
                    val data = formatToNumber(it).toLong()
                    result.sellPrice = data
                }
                BUY_PRICE_INDEX -> {
                    val data = formatToNumber(it).toLong()
                    result.buyPrice = data
                }
                NOTES_INDEX -> {
                    result.notes = it?.trim()
                }
                SUPLIER_NAME_INDEX -> {
                    result.distributor = it?.trim()
                }
                SUPLIER_CONTACT_INDEX -> {

                }
                BARCODE_INDEX -> {
                    result.barCodeId = it?.trim()
                }
                BOKMARK_INDEX -> {
                    var isBookmark = formatToBoolean(it)
                    result.isBookMark = isBookmark
                }
            }
            indexz = indexz + 1
        }

        return result
    }

    private fun formatToNumber(input : String):Int{
        var result = 0
        var data = input.replace(".","")
        data = data.trim()
        try{
            result = data.toInt()
        }
        catch(e:java.lang.Exception){
            e.printStackTrace()
        }
        return result
    }

    private fun formatToBoolean(input:String) : Boolean{
        var result = false
        val data = input.trim()
        if(data.equals("ya",true)){
            result = true
        }

        return result
    }

    override suspend fun exportItemData(): File {
        TODO("Not yet implemented")
    }

}