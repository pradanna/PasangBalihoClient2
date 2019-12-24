package com.genossys.pasangbalihoclient.ui.account

interface AccountListener {

    fun onStartedSignOut()
    fun onSuccessSignOut()
    fun onFailureSignOut(message: String)
}