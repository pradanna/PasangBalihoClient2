package com.genossys.pasangbalihoclient.data.db.repository

import com.genossys.pasangbalihoclient.data.db.PasangBalihoDb
import com.genossys.pasangbalihoclient.data.db.entity.Client
import com.genossys.pasangbalihoclient.data.db.response.ClientResponse
import com.genossys.pasangbalihoclient.data.db.response.PostResponse
import com.genossys.pasangbalihoclient.data.network.api.ApiService
import com.genossys.pasangbalihoclient.data.network.api.SafeApiRequest

class ClientRepository(

    val api: ApiService,
    val db: PasangBalihoDb
) : SafeApiRequest() {


    suspend fun loginClient(email: String, password: String): ClientResponse {
        return apiRequest {
            api.loginClient(email, password)
        }
    }

    suspend fun saveClient(client: Client) = db.clientDao().insert(client)
    suspend fun deleteClient() = db.clientDao().delete()

    fun getClient() = db.clientDao().checkClient()


    suspend fun insertFcmClient(idClient: Int, fcmToken: String): PostResponse {

        return apiRequest {
            api.insertFcmClient(idClient, fcmToken)
        }
    }


    suspend fun deleteFcmClient(fcmToken: String): PostResponse {

        return apiRequest {
            api.deleteFcmClient(fcmToken)
        }
    }
}
















