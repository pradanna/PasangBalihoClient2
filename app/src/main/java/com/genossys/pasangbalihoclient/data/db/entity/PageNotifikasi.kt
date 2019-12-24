package com.genossys.pasangbalihoclient.data.db.entity

import com.genossys.pasangbalihoclient.data.db.entity.Notifikasi
import com.google.gson.annotations.SerializedName

data class PageNotifikasi(

    @SerializedName("current_page")
    val currentPage: Int?,

    @SerializedName("data")
    val notif: List<Notifikasi?>,

    val total: Int?,
    @SerializedName("last_page")
    val lastPage: Int?

)