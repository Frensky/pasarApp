package com.adut.pasar.app.feature.edit

import android.content.Context
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.util.FunctionUtil
import com.adut.pasar.domain.model.Item
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.product_edit__page.*
import java.lang.Exception


class EditFragment : BaseFragment() {
    var selectedId : Long = -1
    var selectedProductName : String = ""

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
                if(focusToSellText){
                    focusToSellText = false
                    edit_et_sell?.requestFocus()
                    simulateClick(edit_et_sell)
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        edit_simpan_btn?.setOnClickListener {
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
            val qtyType = edit_sp_qty_type.selectedItem.toString()
            item.qtyType = qtyType
            item.beli = getFormattedNumber(edit_et_sell?.text.toString())
            item.jual = getFormattedNumber(edit_et_buy?.text.toString())
            item.distributor = edit_et_supplier_name?.text.toString()
            item.barCodeId =  edit_tv_barcode?.text.toString()
            item.isBookmarked =  edit_cb_favorite?.isChecked ?: false
            viewModel.saveProductData(item)
        }

        addCurrencyTextWatcher(edit_et_sell)
        addCurrencyTextWatcher(edit_et_buy)
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

        viewModel.quantityTypeLiveData.observe(this, Observer {
            if(it != null){
                qtyTypeData.clear()
                qtyTypeData.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.submitItemLiveData.observe(this, Observer {
            if(it != null){
                if(it){
                    activity?.finish()
                }
            }
        })

        viewModel.editState.observe(this, Observer {
            if(it != null){
               if(it.type.equals("add")){
                   edit_et_product_name?.setText(it.items.title)
                   edit_txt_title?.setText("Tambah Data Produk")
                   edit_et_qty?.setText("")
                   edit_et_sell?.setText("")
                   edit_et_buy?.setText("")
                   edit_et_supplier_name?.setText("")
                   edit_tv_barcode?.setText("")
                   edit_cb_favorite?.isChecked = false
               }
                else{
                    val selectedItem = it.items
                   edit_txt_title?.setText("Edit Data Produk")
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


                   if(selectedItem.isBookmarked){
                       edit_cb_favorite?.isChecked = true
                   } else{
                       edit_cb_favorite?.isChecked = false
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

    companion object {
        fun newInstance(selectedId:Long , productName:String?): EditFragment {
            val fragments = EditFragment()
            fragments.selectedId = selectedId
            fragments.selectedProductName = productName ?: ""
            return fragments
        }
    }

}