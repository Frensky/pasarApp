package com.adut.pasar.app.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.MyApplication
import java.lang.ref.WeakReference
import javax.inject.Inject

open abstract class BaseFragment : Fragment(),BaseViewMethodInterface {

    private val component by lazy { MyApplication.appComponent }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var wrActivity: WeakReference<Activity>? = null

    protected fun getApplication(): MyApplication {
        return MyApplication.getInstance()
    }

    fun replaceFragment(fragment: Fragment) {
        (activity as BaseActivity).replaceFragment(fragment)
    }

    fun addFragment(fragment: Fragment) {
        (activity as BaseActivity).addFragment(fragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateUI()
        setUICallbacks()
    }

    override fun onAttach(context: Context) {
        component.inject(this)

        if (context is Activity) {
            wrActivity = WeakReference(context)
        }

        super.onAttach(context)
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        wrActivity = WeakReference(activity)
    }

    open fun getWeakReferenceActivity(): Activity? {
        return wrActivity?.get()
    }

    open fun isFragmentSafety(): Boolean {
        if (getWeakReferenceActivity() != null) {
            if (!getWeakReferenceActivity()!!.isFinishing() && isAdded) {
                return true
            }
        }
        return false
    }
}