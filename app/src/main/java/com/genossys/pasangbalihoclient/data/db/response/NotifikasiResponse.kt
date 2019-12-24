package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.PageNotifikasi

data class NotifikasiResponse(
    val respon: String?,
    val message: String?,
    val data: PageNotifikasi
)