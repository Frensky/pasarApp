package com.adut.pasar.app.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adut.pasar.app.R
import com.adut.pasar.app.feature.syncron.SyncronActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUiCallBack()
    }

    private fun setUiCallBack(){
        toSinkronPage?.setOnClickListener{
            SyncronActivity.launchActivity(this)
        }

        toItemPage?.setOnClickListener{
            Toast.makeText(baseContext,"Coming soon",Toast.LENGTH_LONG).show()
        }

        toSettingPage?.setOnClickListener{
            Toast.makeText(baseContext,"Coming soon",Toast.LENGTH_LONG).show()
        }

        toBarCodePage?.setOnClickListener {
            Toast.makeText(baseContext,"Coming soon",Toast.LENGTH_LONG).show()
        }
    }

}