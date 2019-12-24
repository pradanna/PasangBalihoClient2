package com.genossys.pasangbalihoclient.ui.home

interface HomeListener {

    fun onStarted()
    fun onLoadMore()
    fun onRekomendasiLoaded()
    fun onSuccess()
    fun onFailure(message: String)
    fun onTimeOut(s: String)
}