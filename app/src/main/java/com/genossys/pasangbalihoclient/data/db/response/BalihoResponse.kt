package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.PageBaliho

data class BalihoResponse(
    val respon: String?,
    val message: String?,
    val baliho: PageBaliho
)