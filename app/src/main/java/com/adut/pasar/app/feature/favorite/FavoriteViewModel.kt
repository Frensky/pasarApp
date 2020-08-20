package com.adut.pasar.app.feature.favorite

import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.feature.product.ProductViewModel
import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.usecase.item.*
import com.adut.pasar.domain.usecase.setting.IsJualShownUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel@Inject constructor(
    private val getAllItemUseCase: GetTopItemUseCase,
    private val searchItemByKeyWordUseCase: SearchItemByKeyWordUseCase,
    private val searchTitleByKeyWordUseCase: SearchTitleByKeyWordUseCase,
    private val searchItemByBarcodeIdUseCase: GetItemByBarcodeIdUseCase,
    private val isJualShownUseCase: IsJualShownUseCase
): ProductViewModel(getAllItemUseCase, searchItemByKeyWordUseCase, searchTitleByKeyWordUseCase,searchItemByBarcodeIdUseCase, isJualShownUseCase) {

    override fun refreshAllData(){
        viewModelScope.launch {
            val result = getAllItemUseCase.executeFavorite()
            if(result != null){
                allProductData.clear()
                allProductData.addAll(result)
                if(queryString.length < AppConstant.WORD_LIMIT){
                    productLiveData.value = allProductData
                }
            }
        }
    }

    override fun onSearchProduct(key : String?){
        queryString = key ?: ""
        if(queryString.length < AppConstant.WORD_LIMIT){
            productLiveData.value = allProductData
        }
        else{
            viewModelScope.launch {
                val result = searchItemByKeyWordUseCase.executeFavorite(key!!)
                val data = ArrayList<Item>()
                data.addAll(result)
                productLiveData.value = data
            }
        }
    }

    override fun onSearchTitle(key : String?){
        queryString = key ?: ""
        if(queryString.length < AppConstant.WORD_LIMIT){
            titleLiveData.value = ArrayList()
            if(productLiveData.value?.size ?: 0 != allProductData.size){
                productLiveData.value = allProductData
            }
        }
        else{
            viewModelScope.launch {
                val result = searchTitleByKeyWordUseCase.executeFavorite(key!!)
                val data = ArrayList<String>()
                data.addAll(result)
                titleLiveData.value = data
            }
        }
    }


}