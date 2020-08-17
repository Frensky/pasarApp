package com.adut.pasar.app.feature.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.feature.DashboardViewModel
import kotlinx.android.synthetic.main.setting_layout.*


class SettingFragment : BaseFragment() {
    lateinit var dashboardViewModel: DashboardViewModel
    lateinit var viewModel: SettingViewModel

    init {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SettingViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }

    override fun getLayout():Int{
        return R.layout.setting_layout
    }

    override fun initView(){
         viewModel.listenJualStatusData()
         viewModel.listenExportPathData()
    }

    override fun setUICallbacks(){
       setting_page_show_price_switch.setOnCheckedChangeListener { buttonView, isChecked ->
              viewModel.settingJualStatus(isChecked)
       }

        setting_page_export_location_path_edit_button.setOnClickListener {
             changeExportLocationPath()
        }

        setting_page_export_location_path_add_button.setOnClickListener {
            changeExportLocationPath()
        }

    }

    override fun initObserver(){
        dashboardViewModel.reloadSettingView.observe(this, Observer {
            it?.let {
                initView()
            }
        })

        viewModel.jualStatusLiveData.observe(this, Observer {
              it?.let {
                  setting_page_show_price_switch?.isChecked = it
              }
        })

        viewModel.exportPathLiveData.observe(this, Observer {
            val isPathNonExistent = (it == null)
            if(isPathNonExistent) {
                setting_page_export_location_path_add_button.visibility = View.VISIBLE
                setting_page_export_location_path_edit_button.visibility = View.GONE
                setting_page_export_location_path_label.text = "Lokasi eksport belum di simpan, tekan tombol tambah lokasi export untuk menyimpan lokasi eksport data"
            }
            else{
                setting_page_export_location_path_add_button.visibility = View.GONE
                setting_page_export_location_path_edit_button.visibility = View.VISIBLE
                setting_page_export_location_path_label.text = it
            }
        })

    }

    override fun updateUI(){

    }

    companion object {
        fun newInstance(): SettingFragment {
            val fragments = SettingFragment()
            return fragments
        }
    }

    fun changeExportLocationPath(){

    }

}