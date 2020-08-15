package com.adut.pasar.app.base

interface BaseViewMethodInterface {
    fun initView()
    fun setUICallbacks()
    fun getLayout():Int
    fun updateUI()
    fun initObserver()
}