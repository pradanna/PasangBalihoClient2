package com.genossys.pasangbalihoclient.utils

interface SimpleListener {

    fun onStarted()
    fun onSuccess()
    fun onEmpty()
    fun onTimeOut(message: String)
    fun onFailure(message: String)
}