package com.genossys.pasangbalihoclient.data.db.response

import com.genossys.pasangbalihoclient.data.db.entity.PageNews

data class NewsResponse(
    val respon: String?,
    val message: String?,
    val data: PageNews
)