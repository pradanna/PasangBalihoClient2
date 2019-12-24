package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.response.CountResponse
import com.genossys.pasangbalihoclient.data.db.response.DetailTransaksiResponse
import com.genossys.pasangbalihoclient.data.db.response.PostResponse
import com.genossys.pasangbalihoclient.data.db.response.TransaksiResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class TransaksiRepository(
    val api: ApiService
) : SafeApiRequest() {

    suspend fun allDataTransaksiClient(idClient: Int, page: Int): TransaksiResponse {
        return apiRequest {
            api.allDataTransaksiClient(idClient, page)
        }
    }

    suspend fun getDetailTransaksi(id_transaksi: Int): DetailTransaksiResponse {
        return apiRequest {
            api.showDetailTransaksi(id_transaksi)
        }
    }

    suspend fun countNewTransaksiClient(idAdvertiser: Int): CountResponse {
        return apiRequest {
            api.countNewTransaksiClient(idAdvertiser)
        }
    }

    suspend fun getDataTransaksi(idClient: Int, status: String, page: Int): TransaksiResponse {
        return apiRequest {
            api.dataTransaksiClient(idClient, status, page)
        }
    }

}