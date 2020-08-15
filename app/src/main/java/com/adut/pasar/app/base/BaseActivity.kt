package com.adut.pasar.app.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.adut.pasar.app.R
import com.adut.pasar.app.feature.exception.UncaughtExceptionActivity
import com.adut.pasar.app.view.LoadingDialog

abstract class BaseActivity  : AppCompatActivity(),BaseViewMethodInterface {
    val TRANSITION_IN_IN = R.anim.slide_in_left
    val TRANSITION_IN_OUT = R.anim.slide_out_right
    val TRANSITION_OUT_IN = R.anim.slide_in_right
    val TRANSITION_OUT_OUT = R.anim.slide_out_left

    private lateinit var loadingDialog : LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionActivity(this, Thread.getDefaultUncaughtExceptionHandler()))
        overridePendingTransition(TRANSITION_IN_IN, TRANSITION_IN_OUT)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)

        loadingDialog = LoadingDialog(this)

        setContentView(getLayout())
        initObserver()
        initView()
        setUICallbacks()
    }

    fun replaceFragment(fragment: Fragment) {
        replaceFragmentBackstack(fragment,true)
    }

    fun replaceFragmentBackstack(fragment: Fragment,backStackState:Boolean) {
        replaceFragmentId(R.id.container,fragment,backStackState)
    }

    fun replaceFragmentId(id:Int,fragment: Fragment,backStackState:Boolean) {
        if(!isFinishing()){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(id, fragment)
            if(backStackState){
                fragmentTransaction.addToBackStack(fragment::class.java.canonicalName)
            }
            fragmentTransaction.commit()
        }
    }

    fun addFragment(fragment: Fragment) {
        addFragmentBackstack(fragment,true)
    }

    fun addFragmentBackstack(fragment: Fragment,backStackState:Boolean) {
        addFragmentId(R.id.container,fragment,backStackState)
    }

    fun addFragmentId(id:Int,fragment: Fragment,backStackState:Boolean) {
        if(!isFinishing()){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.add(id, fragment)
            if(backStackState){
                fragmentTransaction.addToBackStack(fragment::class.java.canonicalName)
            }
            fragmentTransaction.commit()
        }
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onBackPressed() {
        val stackCount = supportFragmentManager.backStackEntryCount
        if (stackCount > 0) {
            try {
                supportFragmentManager.popBackStack()
            } catch (ignored: IllegalStateException) {
            }
        } else {
            try {
                super.onBackPressed()
            } catch (ignored: IllegalStateException) {
            }
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(TRANSITION_OUT_IN, TRANSITION_OUT_OUT)
    }

    override fun onPause() {
        super.onPause()
        dismissLoadingDialog()
    }

    fun showLoadingDialog(){
        if(!loadingDialog.isShowing){
            loadingDialog.show()
        }
    }

    fun dismissLoadingDialog(){
        if(loadingDialog.isShowing){
            loadingDialog.dismiss()
        }
    }
}