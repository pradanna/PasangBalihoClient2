package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.PasangBalihoDb
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest
import com.genossys.pasangbalihoclient.data.db.response.KotaResponse

class KotaRepository(
    val api: ApiService,
    val db: PasangBalihoDb
) : SafeApiRequest() {


    suspend fun getDataAllKota(): KotaResponse {

        return apiRequest {
            api.getDataAllKota()
        }
    }

}