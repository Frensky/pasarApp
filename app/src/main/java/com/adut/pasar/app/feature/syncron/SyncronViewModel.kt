package com.adut.pasar.app.feature.syncron

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.util.SingleLiveEvent
import com.adut.pasar.domain.model.Item
import com.adut.pasar.domain.model.SyncronState
import com.adut.pasar.domain.usecase.item.GetTopItemUseCase
import com.adut.pasar.domain.usecase.item.TestUseCase
import com.adut.pasar.domain.usecase.setting.GetExportLocationPath
import com.adut.pasar.domain.usecase.setting.SetExportLocationPath
import com.adut.pasar.domain.usecase.syncron.ImportDataUseCase
import com.adut.pasar.domain.util.LogUtil
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class SyncronViewModel @Inject constructor(
    private val importDataUseCase: ImportDataUseCase,
    private val getTopItemUseCase: GetTopItemUseCase,
    private val getExportLocationPath: GetExportLocationPath,
    private val setExportLocationPath: SetExportLocationPath,
    private val testUseCase: TestUseCase
) : ViewModel() {

    val showLoadingDialog: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val syncronViewState: SingleLiveEvent<SyncronViewState?> = SingleLiveEvent()
    val openDirectorySelector : SingleLiveEvent<Boolean?> = SingleLiveEvent()

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

    fun exportCSVData(){
        viewModelScope.launch {
            val path = getExportLocationPath.execute()
            if(path != null) {
                val itemData = getTopItemUseCase.execute() ?: ArrayList<Item>()
                var iterator = 0
                val listItemInSCVString = itemData.map {
                    iterator++
                    it.mapToCSVString(iterator)
                }
                val firstData =
                    "No,Nama Barang,Satuan Barang,Jenis Satuan,Harga Beli (Rp),Harga Jual (Rp),Keterangan,Nama Suplier,No Kontak Suplier,Barcode ID, Simpan ke favorit \n"
                var csvString = listItemInSCVString.reduce { acc, s -> acc + s }
                csvString = firstData + csvString
                LogUtil.logError(csvString)
            } else{
                openDirectorySelector.value = true
            }
        }
    }

    fun setExportPath(path:String){
        viewModelScope.launch {
            setExportLocationPath.execute(path)
            exportCSVData()
        }
    }


    fun testUseCaseProcess(){
        viewModelScope.launch {
            testUseCase.execute()
        }
    }


}
