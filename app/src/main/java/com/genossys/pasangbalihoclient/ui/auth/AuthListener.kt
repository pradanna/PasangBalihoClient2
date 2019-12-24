package com.genossys.pasangbalihoclient.ui.auth

import com.genossys.pasangbalihoclient.data.db.entity.Advertiser
import com.genossys.pasangbalihoclient.data.db.entity.Client

interface AuthListener {

    fun onStarted()
    fun onSuccess(client: Client)
    fun onFailure(message: String)
}