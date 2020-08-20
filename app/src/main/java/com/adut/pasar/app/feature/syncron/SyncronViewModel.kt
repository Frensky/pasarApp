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
import com.adut.pasar.domain.usecase.syncron.ExportDataUseCase
import com.adut.pasar.domain.usecase.syncron.ImportDataUseCase
import com.adut.pasar.domain.util.LogUtil
import com.opencsv.CSVWriter
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileWriter
import javax.inject.Inject

class SyncronViewModel @Inject constructor(
    private val importDataUseCase: ImportDataUseCase,
    private val exportDataUseCase: ExportDataUseCase,
    private val getExportLocationPath: GetExportLocationPath,
    private val setExportLocationPath: SetExportLocationPath
) : ViewModel() {

    val showLoadingDialog: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val syncronViewState: SingleLiveEvent<SyncronViewState?> = SingleLiveEvent()
    val openDirectorySelector : SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val showCSVToast: SingleLiveEvent<String> = SingleLiveEvent()

    fun processCsvData(file : File){
        viewModelScope.launch {
            showLoadingDialog.value = true
            try {
                val response = importDataUseCase.execute(file)
                showLoadingDialog.value = false
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
                val files = exportDataUseCase.execute()
                if(files != null){
                    showCSVToast.value = "Data sudah tersimpan di "+files.absolutePath
                }
                else{
                    showCSVToast.value = "Data gagal di export, mohon coba lain kali"
                }
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

}
