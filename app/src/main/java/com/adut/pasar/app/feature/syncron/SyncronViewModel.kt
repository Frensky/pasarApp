package com.adut.pasar.app.feature.syncron

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.util.SingleLiveEvent
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.model.SyncronState
import com.adut.pasar.domain.usecase.item.GetTopItemUseCase
import com.adut.pasar.domain.usecase.syncron.ImportDataUseCase
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class SyncronViewModel @Inject constructor(
    private val importDataUseCase: ImportDataUseCase,
    private val getTopItemUseCase: GetTopItemUseCase
) : ViewModel() {

    val showLoadingDialog: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val syncronViewState: SingleLiveEvent<SyncronViewState?> = SingleLiveEvent()

    fun processCsvData(file : File){
        viewModelScope.launch {
            showLoadingDialog.value = true
            try {
                val response = importDataUseCase.execute(file)
                showLoadingDialog.value = false
                if(response.isSuccess){
                    val itemData = getTopItemUseCase.execute() ?: ArrayList<Item>()
                    response.message = "Total Item Size is : "+ itemData.size
                }
                val result = SyncronViewState(response)
                syncronViewState.value = result
            } catch (e: Throwable) {
                showLoadingDialog.value = false
                e.printStackTrace()
                val errorResponse = SyncronState(false,"Terjadi Error saat memproses data")
                val result = SyncronViewState(errorResponse)
                syncronViewState.value = result
            }
        }
    }


}
