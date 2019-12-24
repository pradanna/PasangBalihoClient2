package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.Kategori

data class KategoriResponse(
    val respon: String?,
    val message: String?,
    val kategori: List<Kategori>
)