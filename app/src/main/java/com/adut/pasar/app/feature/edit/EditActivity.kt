package com.adut.pasar.app.feature.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseActivity

class EditActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedId = intent.getLongExtra("ITEM_ID",-1)
        val selectedName = intent.getStringExtra("ITEM_NAME")

        replaceFragmentBackstack(EditFragment.newInstance(selectedId,selectedName),false)
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
        fun launchActivity(activity : Activity,itemId:Long){
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("ITEM_ID",itemId)
            activity.startActivity(intent)
        }

        fun launchAddActivity(activity : Activity,productName:String){
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("ITEM_ID",-1)
            intent.putExtra("ITEM_NAME",productName)
            activity.startActivity(intent)
        }
    }

}