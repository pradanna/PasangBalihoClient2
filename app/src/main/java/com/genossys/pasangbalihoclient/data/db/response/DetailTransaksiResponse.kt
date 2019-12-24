package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Transaksi

data class DetailTransaksiResponse(
    val respon: String?,
    val message: String?,
    val transaksi: Transaksi?
)