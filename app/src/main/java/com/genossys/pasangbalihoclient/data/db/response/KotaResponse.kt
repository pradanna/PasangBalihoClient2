package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Kota

data class KotaResponse(
    val respon: String?,
    val message: String?,
    val kota: List<Kota>
)