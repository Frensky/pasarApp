package com.adut.pasar.app.feature.edit

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseActivity
import com.adut.pasar.app.util.AppConstant
import com.adut.pasar.app.util.PermisionHelper
import java.util.jar.Manifest


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
        val ACT_RESULT = 328

        fun launchActivity(activity : Activity, itemId:Long){
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("ITEM_ID",itemId)
            activity.startActivityForResult(intent,ACT_RESULT)
        }

        fun launchAddActivity(activity : Activity,productName:String){
            val intent = Intent(activity, EditActivity::class.java)
            intent.putExtra("ITEM_ID",-1)
            intent.putExtra("ITEM_NAME",productName)
            activity.startActivityForResult(intent,ACT_RESULT)
        }
    }

}