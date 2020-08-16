package com.adut.pasar.app.feature

import android.widget.Toast
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseActivity
import com.adut.pasar.app.feature.edit.EditActivity
import com.adut.pasar.app.feature.favorite.FavoriteFragment
import com.adut.pasar.app.feature.product.ProductFragment
import com.adut.pasar.app.feature.setting.SettingFragment
import com.adut.pasar.app.feature.syncron.SyncronFragment
import kotlinx.android.synthetic.main.dashboard_layout.*

class DashboardActivity : BaseActivity() {

    private var selectedTab = 0

    override fun getLayout():Int{
        return R.layout.dashboard_layout
    }

    override fun setUICallbacks() {
        dashboard_tab_container_harga.setOnClickListener{
            if(selectedTab != 0){
                selectedTab = 0
                dashboard_flipper_main.displayedChild = 0
            }
            updateTabLayout()
        }

        dashboard_tab_container_favorite.setOnClickListener{
            if(selectedTab != 1){
                selectedTab = 1
                dashboard_flipper_main.displayedChild = 1
            }
            updateTabLayout()
        }

        dashboard_tab_container_synch.setOnClickListener{
            if(selectedTab != 2){
                selectedTab = 2
                dashboard_flipper_main.displayedChild = 2
            }
            updateTabLayout()
        }

        dashboard_tab_container_setting.setOnClickListener{
            if(selectedTab != 3){
                selectedTab = 3
                dashboard_flipper_main.displayedChild = 3
            }
            updateTabLayout()
        }

        dashboard_icon_add.setOnClickListener {
            EditActivity.launchAddActivity(this,"")
        }

        dashboard_icon_barcode.setOnClickListener{
            //open settinga activity
            Toast.makeText(this,"coming soon..",Toast.LENGTH_LONG).show()
        }
    }

    override fun initObserver() {

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
        when(selectedTab){
            0->{

            }
            1->{

            }
            2->{

            }
            3->{

            }
        }
    }


}