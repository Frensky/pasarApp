package com.adut.pasar.app.feature

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseActivity
import com.adut.pasar.app.feature.edit.BarcodeActivity
import com.adut.pasar.app.feature.edit.EditActivity
import com.adut.pasar.app.feature.favorite.FavoriteFragment
import com.adut.pasar.app.feature.product.ProductFragment
import com.adut.pasar.app.feature.setting.SettingFragment
import com.adut.pasar.app.feature.syncron.SyncronFragment
import kotlinx.android.synthetic.main.dashboard_layout.*

class DashboardActivity : BaseActivity() {

    private var selectedTab = 0
    private lateinit var viewModel :DashboardViewModel

    override fun getLayout():Int{
        return R.layout.dashboard_layout
    }

    override fun setUICallbacks() {
        dashboard_tab_container_harga.setOnClickListener{
            if(selectedTab != 0){
                selectedTab = 0
                dashboard_flipper_main.displayedChild = 0
                viewModel.reloadProductPage()
            }
            updateTabLayout()
        }

        dashboard_tab_container_favorite.setOnClickListener{
            if(selectedTab != 1){
                selectedTab = 1
                dashboard_flipper_main.displayedChild = 1
                viewModel.reloadFavoritePage()
            }
            updateTabLayout()
        }

        dashboard_tab_container_synch.setOnClickListener{
            if(selectedTab != 2){
                selectedTab = 2
                dashboard_flipper_main.displayedChild = 2
                viewModel.reloadSyncronPage()
            }
            updateTabLayout()
        }

        dashboard_tab_container_setting.setOnClickListener{
            if(selectedTab != 3){
                selectedTab = 3
                dashboard_flipper_main.displayedChild = 3
                viewModel.reloadSettingPage()
            }
            updateTabLayout()
        }

        dashboard_icon_add.setOnClickListener {
            EditActivity.launchAddActivity(this,"")
        }

        dashboard_icon_barcode.setOnClickListener{
            BarcodeActivity.launchBarcodeActivity(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            EditActivity.ACT_RESULT -> {
                if (resultCode == Activity.RESULT_OK) {
                    if(selectedTab == 0){
                        viewModel.updateProductPage()
                    }
                    else if(selectedTab == 1){
                        viewModel.reloadFavoritePage()
                    }
                }
            }
            BarcodeActivity.BARCODE_RESULT -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.extras?.getString("barcode").let {
                        Toast.makeText(this, "Scan result: ${it}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun initObserver() {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    override fun initView() {
        addFragmentId(R.id.dashboard_main_page,ProductFragment.newInstance(),false)
        addFragmentId(R.id.dashboard_favorite_page,FavoriteFragment.newInstance(),false)
        addFragmentId(R.id.dashboard_syncron_page,SyncronFragment.newInstance(),false)
        addFragmentId(R.id.dashboard_setting_page,SettingFragment.newInstance(),false)
    }

    override fun updateUI() {
        updateTabLayout()
    }

    private fun updateTabLayout(){

        dashboard_tab_image_harga.setImageResource(R.drawable.price_nonactive)
        dashboard_tab_image_favorite.setImageResource(R.drawable.favorite_nonactive)
        dashboard_tab_image_synch.setImageResource(R.drawable.sync_nonactive)
        dashboard_tab_image_setting.setImageResource(R.drawable.settting_nonactive)

        dashboard_tab_title_harga.setTextColor(resources.getColor(R.color.colorgrayDark))
        dashboard_tab_title_favorite.setTextColor(resources.getColor(R.color.colorgrayDark))
        dashboard_tab_title_synch.setTextColor(resources.getColor(R.color.colorgrayDark))
        dashboard_tab_title_setting.setTextColor(resources.getColor(R.color.colorgrayDark))

        when(selectedTab){
            0->{
                dashboard_tab_image_harga.setImageResource(R.drawable.price_active)
                dashboard_tab_title_harga.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            1->{
                dashboard_tab_image_favorite.setImageResource(R.drawable.favorite_active)
                dashboard_tab_title_favorite.setTextColor(resources.getColor(R.color.colorPrimary))
             }
            2->{
                dashboard_tab_image_synch.setImageResource(R.drawable.syn_active)
                dashboard_tab_title_synch.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            3->{
                dashboard_tab_image_setting.setImageResource(R.drawable.setting_active)
                dashboard_tab_title_setting.setTextColor(resources.getColor(R.color.colorPrimary))
            }
        }
    }


}