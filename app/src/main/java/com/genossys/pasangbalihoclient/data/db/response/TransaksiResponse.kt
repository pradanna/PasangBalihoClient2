package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.PageTransaksi

data class TransaksiResponse(
    val respon: String?,
    val message: String?,
    val transaksi: PageTransaksi
)