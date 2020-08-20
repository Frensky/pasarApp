package com.adut.pasar.app.feature.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment
import com.adut.pasar.app.feature.DashboardViewModel
import com.adut.pasar.app.feature.product.ProductFragment
import com.adut.pasar.app.feature.product.ProductViewModel
import kotlinx.android.synthetic.main.product_layout.*

class FavoriteFragment : ProductFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        dashboardViewModel = ViewModelProvider(requireActivity()).get(DashboardViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }


    override fun initView() {
        super.initView()
        product_tambah_btn.visibility = View.GONE
        product_empty_text_label.text = "Maaf, data tidak ditemukan / belum masuk kedalam list favorite"
        productSearchView?.visibility = View.GONE
        product_divider?.visibility = View.GONE
    }

    override fun observeDashboardViewModel(){
        dashboardViewModel.reloadFavoriteView.observe(this, Observer {
            it?.let {
                refreshData()
                productSearchView?.clearQuery()
                viewModel.onSearchProduct("")
            }
        })
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            val fragments = FavoriteFragment()
            return fragments
        }
    }

}