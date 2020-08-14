package com.adut.pasar.domain.usecase.item

import android.widget.Toast
import com.adut.pasar.domain.repository.ItemRepository
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.util.LogUtil
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    suspend fun execute() {
        //logging something in here using LogUtil to test etc etc

        val barcode = "1232AA"
        val title = "Minyak Goreng"

        repository.clearData()

        val checkclear = repository.getTopItem().size
        assert(checkclear == 0){
            LogUtil.logError("ITEM GA KEDELTE SEMPURNA")
        }

        val item1 = Item(id=1,isBookmarked = true,title = title,distributor = "Maman",barCodeId = barcode,beli = 12000,jual = 13000,qty = 1,qtyType = "liter")
        val item2 = Item(id=2,isBookmarked = false,title = "Batu",distributor = "Maman",barCodeId = "1232BA",beli = 10000,jual = 12400,qty = 1,qtyType = "kilo")

        repository.saveItemData(item1)
        repository.saveItemData(item2)

        val response = repository.getTopItem()
        LogUtil.log("Top ITEM : "+response)
        assert(response.size == 2){
            LogUtil.logError("JUMLAH ITEM GA SESUAI")
        }

        assert(response.first().title == "Batu"){
            LogUtil.logError("ITEM ga ke ORDER/SORT dengan benar")
        }

        val favorite = repository.getFavoriteItem()
        print("Favorite : "+favorite)
        assert(favorite.size == 1){
            LogUtil.logError("JUMLAH FAVORITE GA SESUAI")
        }

       val quantityType = repository.getItemQuantityType()
        LogUtil.log("Qty Type :"+quantityType)

        val searchedItem = repository.searchItemByKeyWord(title)
        val searchedTitle = repository.searchTitleByKeyWord(title)
        assert(searchedItem.first().title == searchedTitle.first()){
            LogUtil.logError("TITLE GA MATCHING")
        }

        val barcodeItem = repository.getItemByBarcodeId(barcode)
        assert(barcodeItem?.title == item1.title){
            LogUtil.logError("BARCODE ITEM GA SESUAI")
        }

        barcodeItem?.let {
            repository.deleteItem(it)
            val barcodeItem = repository.getItemByBarcodeId(barcode)
            assert(barcodeItem == null){
                LogUtil.logError("BARCODE ITEM STILL EXIST")
            }
        }

        LogUtil.log("Proses Testing database berhasil dilaksanakan")

    }
}