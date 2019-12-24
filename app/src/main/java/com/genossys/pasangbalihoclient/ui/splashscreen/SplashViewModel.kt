package com.genossys.pasangbalihoclient.ui.splashscreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.Advertiser
import com.genossys.pasangbalihoclient.data.db.entity.PageBaliho
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.data.db.response.AdvertiserResponse
import com.genossys.pasangbalihoclient.data.db.response.ClientResponse
import com.genossys.pasangbalihoclient.utils.ApiException
import com.genossys.pasangbalihoclient.utils.CommonListener
import com.genossys.pasangbalihoclient.utils.Coroutines
import com.genossys.pasangbalihoclient.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class SplashViewModel(
    private val clientRepository: ClientRepository

) : ViewModel() {

    private var commonListener: CommonListener? = null
    lateinit var job: Job
    
//    suspend fun getUser(): MutableLiveData<Advertiser> = runBlocking {
//        val advertiser = MutableLiveData<Advertiser>()
//        Coroutines.io {
//            try {
//                advertiser.postValue(advertiserRepository.getAdvertiser2())
//                withContext(Dispatchers.Main) {
//
//                }
//            } catch (e: NullPointerException) {
//                job.cancel()
//            }
//        }
//        return@runBlocking advertiser
//    }

//    suspend fun cekLoginClient(idClient: Int, awal: Boolean): MutableLiveData<ClientResponse> {
//
//        commonListener?.onStarted()
//
//        val client = MutableLiveData<ClientResponse>()
//        job = Job()
//        job = Coroutines.io {
//            try {
//                val balihosResponse = repository.getBaliho(page)
//                balihosResponse.baliho.let {
//                    baliho.postValue(it)
//                    withContext(Dispatchers.Main) {
//                        homeListener?.onRekomendasiLoaded()
//                    }
//                }
//            } catch (e: ApiException) {
//                homeListener?.onFailure(e.message!!)
//                job.cancel()
//            } catch (e: NoInternetException) {
//                homeListener?.onFailure(e.message!!)
//                job.cancel()
//            } catch (e: SocketTimeoutException) {
//                homeListener?.onTimeOut("soket timeout, ulang job lagi")
//                job.cancel()
//            }
//
//        }
//        return baliho
//    }

    fun getClient() = clientRepository.getClient()
}