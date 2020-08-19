package com.adut.pasar.app.feature.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.feature.syncron.SyncronViewState
import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.app.util.SingleLiveEvent
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.usecase.item.GetTopItemUseCase
import com.adut.pasar.domain.usecase.item.SearchItemByKeyWordUseCase
import com.adut.pasar.domain.usecase.item.SearchTitleByKeyWordUseCase
import com.adut.pasar.domain.usecase.setting.IsJualShownUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

open class ProductViewModel @Inject constructor(
    private val getAllItemUseCase: GetTopItemUseCase,
    private val searchItemByKeyWordUseCase: SearchItemByKeyWordUseCase,
    private val searchTitleByKeyWordUseCase: SearchTitleByKeyWordUseCase,
    private val isJualShownUseCase: IsJualShownUseCase
) : ViewModel() {

    val productLiveData: SingleLiveEvent<ArrayList<Item>?> = SingleLiveEvent()
    val titleLiveData: SingleLiveEvent<ArrayList<String>?> = SingleLiveEvent()
    val segmentUILiveData: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    internal val allProductData : ArrayList<Item>
    internal var queryString = ""

    init {
        allProductData = ArrayList()
    }


    open fun refreshAllData(){
        viewModelScope.launch {
            val result = getAllItemUseCase.execute()
            if(result != null){
                allProductData.clear()
                allProductData.addAll(result)
                if(queryString.length < AppConstant.WORD_LIMIT){
                    productLiveData.value = allProductData
                }
            }
        }
    }

    open fun onSearchProduct(key : String?){
        queryString = key ?: ""
        if(queryString.length < AppConstant.WORD_LIMIT){
            productLiveData.value = allProductData
        }
        else{
            viewModelScope.launch {
                val result = searchItemByKeyWordUseCase.execute(key!!)
                val data = ArrayList<Item>()
                data.addAll(result)
                productLiveData.value = data
            }
        }
    }

    open fun onSearchTitle(key : String?){
        queryString = key ?: ""
        if(queryString.length < AppConstant.WORD_LIMIT){
            titleLiveData.value = ArrayList()
            if(productLiveData.value?.size ?: 0 != allProductData.size){
                productLiveData.value = allProductData
            }
        }
        else{
            viewModelScope.launch {
                val result = searchTitleByKeyWordUseCase.execute(key!!)
                val data = ArrayList<String>()
                data.addAll(result)
                titleLiveData.value = data
            }
        }
    }

    fun checkSegmentUi(){
        viewModelScope.launch {
            val result = isJualShownUseCase.execute()
            segmentUILiveData.value = result
        }
    }

}