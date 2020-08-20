package com.adut.pasar.app.feature.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adut.pasar.app.util.SingleLiveEvent
import com.adut.pasar.domain.usecase.setting.GetExportLocationPath
import com.adut.pasar.domain.usecase.setting.IsJualShownUseCase
import com.adut.pasar.domain.usecase.setting.SetExportLocationPath
import com.adut.pasar.domain.usecase.setting.SetJualShownUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val getExportLocationPath: GetExportLocationPath,
    private val isJualShownUseCase: IsJualShownUseCase,
    private val setExportLocationPath: SetExportLocationPath,
    private val setJualShownUseCase: SetJualShownUseCase
) : ViewModel() {

    val jualStatusLiveData: SingleLiveEvent<Boolean?> = SingleLiveEvent()
    val exportPathLiveData : SingleLiveEvent<String?> = SingleLiveEvent()

    fun settingJualStatus(isShown:Boolean){
        viewModelScope.launch {
            setJualShownUseCase.execute(isShown)
        }
    }

    fun listenJualStatusData(){
        viewModelScope.launch {
            val result = isJualShownUseCase.execute()
            jualStatusLiveData.value = result
        }
    }

    fun setExportPath(path:String){
        viewModelScope.launch {
            setExportLocationPath.execute(path)
            exportPathLiveData.value = path
        }
    }

    fun listenExportPathData(){
        viewModelScope.launch {
            val result = getExportLocationPath.execute()
            exportPathLiveData.value = result
        }
    }


}