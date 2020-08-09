package com.adut.pasar.app.feature.syncron

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseActivity

class SyncronActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragmentBackstack(SyncronFragment.newInstance(),false)
    }

    override fun getLayout(): Int {
        return R.layout.activity_fragment_container
    }


    override fun initObserver() {

    }

    override fun initView() {

    }

    override fun setUICallbacks() {

    }

    override fun updateUI() {

    }

    companion object {
        fun launchActivity(activity : Activity){
            val intent = Intent(activity, SyncronActivity::class.java)
            activity.startActivity(intent)
        }
    }

}
