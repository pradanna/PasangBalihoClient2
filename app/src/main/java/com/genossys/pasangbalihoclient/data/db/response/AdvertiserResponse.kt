package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Advertiser

data class AdvertiserResponse(
    val respon: String?,
    val message: String?,
    val advertiser: Advertiser?
)