package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.response.NotifikasiResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class NotifRepository(
    val api: ApiService
) : SafeApiRequest() {

    suspend fun getDataNotif(page: Int, idAdv: Int): NotifikasiResponse {

        return apiRequest {
            api.getDataNotifikasi(page, idAdv)
        }
    }

}