package com.adut.pasar.app.base

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.adut.pasar.app.R
import com.adut.pasar.app.feature.exception.UncaughtExceptionActivity

abstract class BaseActivity  : AppCompatActivity(),BaseViewMethodInterface {
    val TRANSITION_IN_IN = R.anim.slide_in_left
    val TRANSITION_IN_OUT = R.anim.slide_out_right
    val TRANSITION_OUT_IN = R.anim.slide_in_right
    val TRANSITION_OUT_OUT = R.anim.slide_out_left

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.setDefaultUncaughtExceptionHandler(UncaughtExceptionActivity(this, Thread.getDefaultUncaughtExceptionHandler()))
        overridePendingTransition(TRANSITION_IN_IN, TRANSITION_IN_OUT)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        super.onCreate(savedInstanceState)

        setContentView(getLayout())
        initObserver()
        initView()
        setUICallbacks()
    }

    fun replaceFragment(fragment: Fragment) {
        replaceFragmentBackstack(fragment,true)
    }

    fun replaceFragmentBackstack(fragment: Fragment,backStackState:Boolean) {
        if(!isFinishing()){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container, fragment)
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
        if(!isFinishing()){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right);
            fragmentTransaction.add(R.id.container, fragment)
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
}