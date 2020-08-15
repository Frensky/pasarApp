package com.adut.pasar.app.feature.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.adut.pasar.app.R
import com.adut.pasar.app.base.BaseFragment

class FavoriteFragment : BaseFragment() {
    lateinit var viewModel: FavoriteViewModel

    init {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)
        return inflater.inflate(getLayout(), container, false)
    }

    override fun getLayout():Int{
        return R.layout.favorite_layout
    }

    override fun initView(){

    }

    override fun setUICallbacks(){

    }

    override fun initObserver(){

    }

    override fun updateUI(){

    }

    companion object {
        fun newInstance(): FavoriteFragment {
            val fragments = FavoriteFragment()
            return fragments
        }
    }

}