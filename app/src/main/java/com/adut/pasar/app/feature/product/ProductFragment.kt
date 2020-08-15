package com.adut.pasar.app.feature.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment

class ProductFragment : BaseFragment() {
    lateinit var viewModel: ProductViewModel

    init {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }

    override fun getLayout():Int{
        return R.layout.product_layout
    }

    override fun initView(){

    }

    override fun setUICallbacks(){

    }

    override fun initObserver(){

    }

    override fun updateUI(){

    }

    companion object {
        fun newInstance(): ProductFragment {
            val fragments = ProductFragment()
            return fragments
        }
    }

}