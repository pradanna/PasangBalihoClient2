package com.genossys.pasangbalihoclient.data.db.entity

import com.genossys.pasangbalihoclient.data.db.entity.News
import com.google.gson.annotations.SerializedName

data class PageNews(

    @SerializedName("current_page")
    val currentPage: Int?,

    @SerializedName("data")
    val news: List<News?>,

    val total: Int?,
    @SerializedName("last_page")
    val lastPage: Int?

)