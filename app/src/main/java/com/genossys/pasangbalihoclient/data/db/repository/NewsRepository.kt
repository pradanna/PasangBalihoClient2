package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.response.NewsResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class NewsRepository(
    val api: ApiService
) : SafeApiRequest() {

    suspend fun getDataNews(page: Int): NewsResponse {

        return apiRequest {
            api.getDataNews(page)
        }
    }

}