package com.adut.pasar.app.base

interface BaseViewMethodInterface {
   open fun initView()
   open fun setUICallbacks()
   open fun getLayout():Int
   open fun updateUI()
   open fun initObserver()
}