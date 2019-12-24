package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.PasangBalihoDb
import com.genossys.pasangbalihoclient.data.db.entity.InputBaliho
import com.genossys.pasangbalihoclient.data.db.response.BalihoResponse
import com.genossys.pasangbalihoclient.data.db.response.DetailBalihoResponse
import com.genossys.pasangbalihoclient.data.db.response.SliderResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class BalihoRepository(
    val api: ApiService,
    val db: PasangBalihoDb
) : SafeApiRequest() {

    suspend fun getDetail(id: Int): DetailBalihoResponse {

        return apiRequest {
            api.showDetailBaliho(id)
        }
    }

    suspend fun getBalihoClient(
        idClient: Int,
        kota: String,
        kategori: String,
        sortby: String,
        urutan: String,
        tambahan: String,
        page: Int
    ): BalihoResponse {

        return apiRequest {
            api.getBalihoClient(idClient, kota, kategori, sortby, urutan, tambahan, page)
        }
    }

  suspend fun inputLocalBaliho(inputBaliho: InputBaliho) = db.balihoDao().inputLocalBaliho(inputBaliho)

}