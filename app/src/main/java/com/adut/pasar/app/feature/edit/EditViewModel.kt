package com.adut.pasar.app.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.util.SingleLiveEvent
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.usecase.item.GetFavoriteItemUseCase
import com.adut.pasar.domain.usecase.item.GetItemByIdUseCase
import com.adut.pasar.domain.usecase.item.GetQuantityTypeUseCase
import com.adut.pasar.domain.usecase.item.ItemDAOUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditViewModel @Inject constructor(
    private val getQuantityTypeUseCase: GetQuantityTypeUseCase,
    private val getItemByIdUseCase: GetItemByIdUseCase,
    private val saveItemUseCase: ItemDAOUseCase
) : ViewModel() {

    private var initialAddProductName : String = ""

    val showLoadingDialog: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val quantityTypeLiveData: SingleLiveEvent<ArrayList<String>?> = SingleLiveEvent()
    val editState: SingleLiveEvent<EditViewState> = SingleLiveEvent()
    val submitItemLiveData: SingleLiveEvent<Boolean?> = SingleLiveEvent()

    var selectedProduct : Item? = null

    fun setInitialAddProductTitle(name:String){
        initialAddProductName = name
    }

    fun getQuantityTypeData(){
        viewModelScope.launch {
            val result = getQuantityTypeUseCase.execute()
            if(!result.isNullOrEmpty()){
                val list = ArrayList<String>()
                list.addAll(result)
                quantityTypeLiveData.value = list
            }
        }
    }

    fun loadProductData(product_id:Long){
        if(product_id < 0){
           triggerAddAction()
            return
        }

        viewModelScope.launch {
            showLoadingDialog.value = true
            delay(2000)
            val result = getItemByIdUseCase.execute(product_id)
            if(result != null){
                selectedProduct = result
                val state = EditViewState("update",result)
                editState.value = state
            }
            else{
                triggerAddAction()
            }
            showLoadingDialog.value = false
        }
    }

    fun saveProductData(item : Item){
        viewModelScope.launch {
            if(selectedProduct != null){
                item.id = selectedProduct!!.id
                saveItemUseCase.updateItem(item)
            }
            else{
                saveItemUseCase.saveItemData(item)
            }
            submitItemLiveData.value = true
        }
    }

    private fun triggerAddAction(){
        selectedProduct = null
        val newItem = Item()
        newItem.id = -1
        newItem.title = initialAddProductName
        val state = EditViewState("add",newItem)
        editState.value = state
    }

}