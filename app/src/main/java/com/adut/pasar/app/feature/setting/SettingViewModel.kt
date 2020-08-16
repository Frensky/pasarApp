package com.adut.pasar.app.feature.setting

import androidx.lifecycle.ViewModel
import com.adut.pasar.domain.usecase.setting.GetExportLocationPath
import com.adut.pasar.domain.usecase.setting.IsJualShownUseCase
import com.adut.pasar.domain.usecase.setting.SetExportLocationPath
import com.adut.pasar.domain.usecase.setting.SetJualShownUseCase
import javax.inject.Inject

class SettingViewModel @Inject constructor(
    private val getExportLocationPath: GetExportLocationPath,
    private val isJualShownUseCase: IsJualShownUseCase,
    private val setExportLocationPath: SetExportLocationPath,
    private val setJualShownUseCase: SetJualShownUseCase
) : ViewModel() {

}