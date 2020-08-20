package com.adut.pasar.app.feature

import androidx.lifecycle.ViewModel
import com.adut.pasar.app.util.SingleLiveEvent

class DashboardViewModel () : ViewModel() {

    val reloadFavoriteView: SingleLiveEvent<Int?> = SingleLiveEvent()
    val reloadSyncronView: SingleLiveEvent<Int?> = SingleLiveEvent()
    val reloadSettingView: SingleLiveEvent<Int?> = SingleLiveEvent()

    val reloadProductView: SingleLiveEvent<Int?> = SingleLiveEvent()
    val updateProductView: SingleLiveEvent<Int?> = SingleLiveEvent()
    val searchProductByBarCode: SingleLiveEvent<String?> = SingleLiveEvent()

    fun reloadProductPage(){
        reloadProductView.value = reloadProductView.value?:0 + 1
    }

    fun updateProductPage(){
        updateProductView.value = updateProductView.value?:0 + 1
    }

    fun reloadFavoritePage(){
        reloadFavoriteView.value = reloadFavoriteView.value?:0 + 1
    }

    fun reloadSyncronPage(){
        reloadSyncronView.value = reloadSyncronView.value?:0 + 1
    }

    fun reloadSettingPage(){
        reloadSettingView.value = reloadSettingView.value?:0 + 1
    }

    fun searchBarCode(barCode:String){
        searchProductByBarCode.value = barCode
    }


}