package com.adut.pasar.app.feature.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.feature.BarcodeActivity
import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.app.util.FunctionUtil
import com.adut.pasar.app.util.PermisionHelper
import com.adut.pasar.app.view.YesNoDialog
import com.adut.pasar.domain.model.Item
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.product_edit__page.*
import java.lang.Exception


class EditFragment : BaseFragment() {
    var selectedId : Long = -1
    var selectedProductName : String = ""
    var selectedBarCode = ""

    lateinit var viewModel: EditViewModel
    val qtyTypeData : ArrayList<String> = ArrayList()
    lateinit var adapter : ArrayAdapter<String>
    var focusToSellText = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(EditViewModel::class.java)
        viewModel.setInitialAddProductTitle(selectedProductName)

        return inflater.inflate(getLayout(), container, false)
    }

    override fun onResume() {
        super.onResume()
        if(!selectedBarCode.isNullOrEmpty()){
            edit_tv_barcode.text = selectedBarCode
        }
    }

    override fun getLayout():Int{
        return R.layout.product_edit__page
    }

    override fun initView(){
        adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, qtyTypeData)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        edit_sp_qty_type.setAdapter(adapter)
        viewModel.getQuantityTypeData()
        viewModel.loadProductData(selectedId)
    }

    override fun setUICallbacks(){
        back_btn_icn?.setOnClickListener {
            activity?.onBackPressed()
        }

        edit_et_qty?.setOnEditorActionListener(OnEditorActionListener { textView, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                focusToSellText = true
                hideKeyboard(requireView())
                textView.clearFocus()
                edit_sp_qty_type?.requestFocus()
                edit_sp_qty_type?.performClick()
            }
            true
        })

        edit_ic_qty_type?.setOnClickListener{
            edit_sp_qty_type?.requestFocus()
            edit_sp_qty_type?.performClick()
        }

        edit_sp_qty_type?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long
            ) {

                if(i == qtyTypeData.count()-1){
                   edit_tl_satuan_item.visibility = View.VISIBLE
                   edit_et_satuan.setText("")
                   edit_et_satuan.requestFocus()
                   simulateClick(edit_et_satuan)
                }
                else if(focusToSellText){
                    edit_tl_satuan_item.visibility = View.GONE
                    focusToSellText = false
                    edit_et_sell?.requestFocus()
                    simulateClick(edit_et_sell)
                }
                else{
                    edit_tl_satuan_item.visibility = View.GONE
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        edit_simpan_btn?.setOnClickListener {
            if(isDataValid()){
                val item = Item()
                item.title = edit_et_product_name?.text.toString()
                val qty = edit_et_qty.text.toString()
                var qtyItem = 0
                if(!qty.isNullOrEmpty()){
                    try {
                        qtyItem = qty.toInt()
                    }
                    catch (e:Exception){

                    }
                }
                item.qty = qtyItem
                var qtyType = edit_sp_qty_type.selectedItem.toString()
                if(edit_sp_qty_type.selectedItem.toString().toLowerCase().equals("lainnya")){
                     qtyType = edit_et_satuan.text.toString()
                }
                item.qtyType = qtyType
                item.beli = getFormattedNumber(edit_et_sell?.text.toString())
                item.jual = getFormattedNumber(edit_et_buy?.text.toString())
                item.distributor = edit_et_supplier_name?.text.toString()

                var barcodeId =  edit_tv_barcode?.text.toString()
                if(barcodeId.trim().equals("---")){
                    barcodeId = ""
                }
                item.barCodeId =  barcodeId
                item.isBookmarked = viewModel.isProductBookmarked()

                viewModel.saveProductData(item)
            }
        }

        fav_icn?.setOnClickListener {
            viewModel.updateBookmarkState()
        }

        edit_hapus_btn?.setOnClickListener {
            showDeleteDialog()
        }

        edit_barcode_btn?.setOnClickListener {
            if ( PermisionHelper.hasPermissions(requireContext(), *AppConstant.CAMERA_PERMISION)) {
                BarcodeActivity.launchBarcodeActivity(this)
            } else {
                requestPermissions( AppConstant.CAMERA_PERMISION, 1)
            }
        }

        addCurrencyTextWatcher(edit_et_sell)
        addCurrencyTextWatcher(edit_et_buy)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                edit_barcode_btn?.callOnClick()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            BarcodeActivity.BARCODE_RESULT -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.extras?.getString("barcode")?.let {
                        selectedBarCode = it
                    }
                }
            }
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

        viewModel.showErrorDialog.observe(this, Observer { error ->
            if(!error.isNullOrEmpty()){
                Toast.makeText(requireContext(),error,Toast.LENGTH_LONG).show()
            }
        })

        viewModel.quantityTypeLiveData.observe(this, Observer {
            if(it != null){
                qtyTypeData.clear()
                it.add("lainnya")
                qtyTypeData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.submitItemLiveData.observe(this, Observer {
            if(it != null){
                if(it){
                    val returnIntent = Intent()
                    returnIntent.putExtra("reloadData", true)
                    activity?.setResult(Activity.RESULT_OK, returnIntent)
                    activity?.finish()
                }
            }
        })

        viewModel.bookmarkState.observe(this, Observer {
            if(it ?: false){
                fav_icn?.setImageResource(R.drawable.bookmark_check)
            }
            else{
                fav_icn?.setImageResource(R.drawable.bookmark_uncheck)
            }
        })

        viewModel.editState.observe(this, Observer {
            if(it != null){
               if(it.type.equals("add")){
                   edit_et_product_name?.setText(it.items.title)
                   edit_txt_title?.setText("Tambah Data Produk")
                   edit_hapus_btn?.visibility = View.GONE

                   edit_et_qty?.setText("")
                   edit_et_sell?.setText("")
                   edit_et_buy?.setText("")
                   edit_et_supplier_name?.setText("")
                   edit_tv_barcode?.setText("")
                   fav_icn?.setImageResource(R.drawable.bookmark_uncheck)
               }
                else{
                    val selectedItem = it.items
                   edit_txt_title?.setText("Edit Data Produk")
                   edit_hapus_btn?.visibility = View.VISIBLE

                   edit_et_product_name?.setText(selectedItem.title)
                   edit_et_supplier_name?.setText(selectedItem.distributor)

                    val qty = selectedItem.qty
                    var qtyString = ""
                    if(qty > 0){
                       qtyString = qty.toString()
                    }
                    edit_et_qty?.setText(qtyString)

                   val qtyType = selectedItem.qtyType
                   if(!qtyType.isNullOrEmpty()){
                       val qtyIndex = qtyTypeData.indexOf(qtyType)
                       if(qtyIndex >= 0){
                           edit_sp_qty_type?.setSelection(qtyIndex)
                           edit_tl_satuan_item.visibility = View.GONE
                       }
                       else{
                           edit_sp_qty_type?.setSelection(qtyTypeData.count()-1)
                           edit_tl_satuan_item.visibility = View.VISIBLE
                           edit_et_satuan.setText(qtyType)
                       }
                   }

                   val sell = selectedItem.beli
                   var sellString = ""
                   if(qty > 0){
                       sellString = sell.toString()
                   }
                   edit_et_sell?.setText(sellString)

                   val buy = selectedItem.jual
                   var buyString = ""
                   if(buy > 0){
                       buyString = buy.toString()
                   }
                   edit_et_buy?.setText(buyString)

                   if(selectedItem.barCodeId.isNullOrEmpty()){
                       edit_tv_barcode?.setText("---")
                   }
                   else{
                       edit_tv_barcode?.setText(selectedItem.barCodeId)
                   }
               }
            }
        })
    }

    override fun updateUI(){
    }


    fun addCurrencyTextWatcher(etInput:TextInputEditText){
        etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
            {

            }

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                etInput.removeTextChangedListener(this)
                try {
                    val formatted: String = FunctionUtil.formatStringToRupiah(charSequence.toString())+""
                    etInput.setText(formatted)
                    etInput.setSelection(formatted.length)
                } catch (e: IndexOutOfBoundsException) {
                    val formatted: String =  FunctionUtil.formatStringToRupiah("99999999")+""
                    etInput.setText(formatted)
                    etInput.setSelection(formatted.length)
                }
                etInput.addTextChangedListener(this)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    fun simulateClick(etInput:TextInputEditText?){
        Handler().postDelayed({
            etInput?.dispatchTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0)
            )

            etInput?.dispatchTouchEvent(
                MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0)
            )

            etInput?.setSelection((etInput?.getText()?:"").length)
        }, 200)
    }

    fun getFormattedNumber(input : String) : Long{
        val result = FunctionUtil.rupiahStringToDouble(input).toLong()
        return result
    }

    private fun hideKeyboard(view : View){
        if (activity != null) {
            val inputManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            // check if no view has focus:
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun showDeleteDialog(){
        val dialogs = YesNoDialog()
        dialogs.setContent("Apakah anda yakin untuk menghapus data ini?")

        dialogs.setListener(object : YesNoDialog.YesNoDialogListener{
            override fun onYesClick() {
                viewModel.deleteProductData()
            }

            override fun onDismmisClick() {

            }
        })

        val ft = requireActivity().supportFragmentManager.beginTransaction()
        ft.add(dialogs, null)
        ft.commitAllowingStateLoss()
    }

    private fun isDataValid():Boolean{
        var isValid = true

        if(edit_et_product_name.text.toString().isNullOrEmpty()){
            isValid = false
            edit_tl_product_name.error = "Tolong masukkan nama Item"
        }
        else{
            edit_tl_product_name.error = ""
        }

        if(edit_et_qty.text.toString().isNullOrEmpty()){
            isValid = false
            setQtyError(true)
        }
        else{
            var qtyItem = 0
            try {
                qtyItem = edit_et_qty.text.toString().toInt()
            }
            catch (e:Exception){

            }

            if(qtyItem <= 0){
                isValid = false
                setQtyError(true)
            }
            else{
                setQtyError(false)
            }
        }

        return isValid
    }

    private fun setQtyError(isError:Boolean){
        if(isError){
            edit_label_qty.setTextColor(resources.getColor(R.color.color_text_red))
            edit_error_qty.visibility = View.VISIBLE
        }
        else{
            edit_label_qty.setTextColor(resources.getColor(R.color.color_text_dark))
            edit_error_qty.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(selectedId:Long , productName:String?): EditFragment {
            val fragments = EditFragment()
            fragments.selectedId = selectedId
            fragments.selectedProductName = productName ?: ""
            return fragments
        }
    }

}