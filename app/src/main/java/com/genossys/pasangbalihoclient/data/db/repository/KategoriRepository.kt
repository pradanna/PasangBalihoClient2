package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.PasangBalihoDb
import com.genossys.pasangbalihoclient.data.db.response.KategoriResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class KategoriRepository(
    val api: ApiService,
    val db: PasangBalihoDb
) : SafeApiRequest() {


    suspend fun getDataAllKategori(): KategoriResponse {

        return apiRequest {
            api.getDataAllKategori()
        }
    }

}