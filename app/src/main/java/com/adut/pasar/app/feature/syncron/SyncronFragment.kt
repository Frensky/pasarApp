package com.adut.pasar.app.feature.syncron

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.app.util.PermisionHelper
import ir.androidexception.filepicker.dialog.SingleFilePickerDialog
import ir.androidexception.filepicker.interfaces.OnCancelPickerDialogListener
import ir.androidexception.filepicker.interfaces.OnConfirmDialogListener
import kotlinx.android.synthetic.main.syncron_page_layout.*
import java.io.File
import javax.inject.Inject

class SyncronFragment : BaseFragment() {
    lateinit var viewModel: SyncronViewModel
    var shouldOpenFileSelector = false

    init {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SyncronViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }

    override fun getLayout():Int{
        return R.layout.syncron_page_layout
    }

    override fun initView(){

    }

    override fun setUICallbacks(){
        btnImport?.setOnClickListener {
            openFileSelector()
        }

        btnExport?.setOnClickListener {
            Toast.makeText(context,"Coming Soon",Toast.LENGTH_SHORT).show()
        }

        btnBack?.setOnClickListener{
            activity?.onBackPressed()
        }
    }

    private fun openFileSelector(){
        if (PermisionHelper.hasPermissions(activity, *AppConstant.FILE_PERMISION)) {
            val singleFilePickerDialog = SingleFilePickerDialog(requireActivity(),
                OnCancelPickerDialogListener {

                },
                OnConfirmDialogListener { files: Array<File> ->
                    if(!files.isNullOrEmpty()){
                        viewModel.processCsvData(files[0])
                    }
                }
            )
            singleFilePickerDialog.show()
        } else {
            requestPermission()
        }
    }

    override fun initObserver(){
        viewModel.showLoadingDialog.observe(this, Observer { isLoading ->
            if(isLoading!= null){
                if(isLoading){
                    showLoadingDialog()
                }
                else{
                    dismissLoadingDialog()
                }
            }
        })

        viewModel.syncronViewState.observe(this, Observer { data ->
                if(data?.data != null){
                    val message = data.data.message
                    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
                }
        })
    }

    override fun updateUI(){

    }

    private fun requestPermission() {
        requestPermissions( AppConstant.FILE_PERMISION,
            1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (PermisionHelper.hasPermissions(activity, *AppConstant.FILE_PERMISION)) {
                    shouldOpenFileSelector = true
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (shouldOpenFileSelector) {
            shouldOpenFileSelector = false
            openFileSelector()
        }
    }

    companion object {
        fun newInstance(): SyncronFragment {
            val fragments = SyncronFragment()
            return fragments
        }
    }

}