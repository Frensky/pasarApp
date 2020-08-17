package com.adut.pasar.app.base

import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.app.util.PermisionHelper
import ir.androidexception.filepicker.dialog.DirectoryPickerDialog
import ir.androidexception.filepicker.dialog.SingleFilePickerDialog
import ir.androidexception.filepicker.interfaces.OnCancelPickerDialogListener
import ir.androidexception.filepicker.interfaces.OnConfirmDialogListener
import java.io.File

interface  OpenFileProtocolClass{
    fun openFileSelector()
    fun openDirectoryPathSelector()
    var shouldOpenFileSelector : Boolean
}

abstract class OpenFileFragment : BaseFragment(), OpenFileProtocolClass{

    override var shouldOpenFileSelector = false

    override fun openFileSelector(){
        if (PermisionHelper.hasPermissions(activity, *AppConstant.FILE_PERMISION)) {
            val singleFilePickerDialog = SingleFilePickerDialog(requireActivity(),
                OnCancelPickerDialogListener {

                },
                OnConfirmDialogListener { files: Array<File> ->
                    if(!files.isNullOrEmpty()){
                        fileDataDidSelected(files[0])
                    }
                }
            )
            singleFilePickerDialog.show()
        } else {
            requestPermission()
        }
    }

    override fun openDirectoryPathSelector(){
        if (PermisionHelper.hasPermissions(activity, *AppConstant.FILE_PERMISION)) {

            val directoryPickerDialog = DirectoryPickerDialog(requireActivity(),
                OnCancelPickerDialogListener {

                },
                OnConfirmDialogListener { files: Array<File> ->
                    if(!files.isNullOrEmpty()){
                        directoryPathDidSelected(files[0].path)
                    }
                }
            )
            directoryPickerDialog.show()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        requestPermissions( AppConstant.FILE_PERMISION, 1)
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
            onFileSelectorPermissionApproved()
        }
    }

    open fun onFileSelectorPermissionApproved(){
        openFileSelector()
    }

    open fun fileDataDidSelected(file:File){
        //override if needed
    }

    open fun directoryPathDidSelected(path:String){
        //override if needed
    }


}