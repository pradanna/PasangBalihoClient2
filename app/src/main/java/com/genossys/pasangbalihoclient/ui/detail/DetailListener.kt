package com.genossys.pasangbalihoclient.ui.detail

interface DetailListener {

    fun onStarted()
    fun onFotoLoaded()
    fun onDetailLoaded()
    fun onSuccess()
    fun onFailure(message: String)
    fun onTimeOut(message: String)
}