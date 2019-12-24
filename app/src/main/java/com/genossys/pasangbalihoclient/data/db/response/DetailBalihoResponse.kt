package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Baliho
import com.genossys.pasangbalihoclient.data.db.entity.FotoBaliho
import com.genossys.pasangbalihoclient.data.db.entity.Transaksi

data class DetailBalihoResponse(
    val respon: String?,
    val message: String?,
    val baliho: Baliho?,
    val foto: List<FotoBaliho>,
    val transaksi: List<Transaksi>
)