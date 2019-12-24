package com.genossys.pasangbalihoclient.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.data.db.response.ClientResponse
import com.genossys.pasangbalihoclient.utils.ApiException
import com.genossys.pasangbalihoclient.utils.Coroutines
import com.genossys.pasangbalihoclient.utils.NoInternetException
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException

class AuthViewModel(
    val clientRepository: ClientRepository
) : ViewModel() {

    var authListener: AuthListener? = null
    lateinit var job: Job

    fun loginClient(email: String, password: String, fcmToken: String): MutableLiveData<ClientResponse> {
        authListener!!.onStarted()

        job = Job()
        val client = MutableLiveData<ClientResponse>()
        Coroutines.io {
            try {
                val clientResponse = clientRepository.loginClient(email, password)
                clientResponse.let {
                    client.postValue(it)
                    if (it.respon != "failure") {
                        authListener!!.onSuccess(it.client!!)
                        clientRepository.saveClient(it.client)
                        Coroutines.main {
                            clientRepository.insertFcmClient(it.client.idClient!!, fcmToken)
                        }
                    } else {
                        authListener!!.onFailure(it.message!!)
                        job.cancel()
                    }
                }
            } catch (e: ApiException) {
                job.cancel()
                authListener!!.onFailure("terjadi kesalahan, coba lagi nanti ya")
            } catch (e: NoInternetException) {
                job.cancel()
                authListener!!.onFailure("tidak ada koneksi internet, coba cek koneksi internetmu...")
            } catch (e: SocketTimeoutException) {
                job.cancel()
                authListener!!.onFailure("koneksi internet mu sedang eror / lambat ya, coba lagi nanti")
            }
        }
        return client
    }
//    val user = repository.getAdvertiser()

//    fun getLoggedInAdvertiser() = repository.getAdvertiser()

//    fun deleteAdvertiser() {
//
//
//        Coroutines.main {
//
//            try {
//                repository.deleteAdvertiser()
//                Log.d("on sign out", "coba sign out")
//                authListener?.onFailure("Anda berhasil keluar")
//                return@main
//
//            } catch (e: ApiException) {
//                authListener?.onFailure(e.message!!)
//            } catch (e: NoInternetException) {
//                authListener?.onFailure(e.message!!)
//            }
//
//        }
//
//    }

}