package com.genossys.pasangbalihoclient.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.PageTransaksi
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository
import com.genossys.pasangbalihoclient.data.db.response.CountResponse
import com.genossys.pasangbalihoclient.utils.*
import kotlinx.coroutines.Job
import java.net.SocketTimeoutException

class HomeViewModel(
    private val repository: BalihoRepository,
    private val transaksiRepository: TransaksiRepository,
    private val clientRepository: ClientRepository

) : ViewModel() {

    var homeListener: HomeListener? = null
    lateinit var job: Job


    suspend fun countNewTransaksiClient(idClient: Int): MutableLiveData<CountResponse> {

        val count = MutableLiveData<CountResponse>()
        job = Job()
        job = Coroutines.io {
            try {
                val countResponse = transaksiRepository.countNewTransaksiClient(idClient)
                countResponse.let {
                    count.postValue(it)
                }
            } catch (e: ApiException) {
                job.cancel()
            } catch (e: NoInternetException) {
                job.cancel()
            } catch (e: SocketTimeoutException) {
                job.cancel()
            }

        }
        return count
    }


    suspend fun allDataTransaksiClient(
        idClient: Int,
        page: Int
    ): MutableLiveData<PageTransaksi> {

        homeListener!!.onStarted()
        val data = MutableLiveData<PageTransaksi>()
        job = Job()
        job = Coroutines.io {
            try {
                val transaksiResponse = transaksiRepository.allDataTransaksiClient(idClient, page)
                transaksiResponse.let {
                    data.postValue(it.transaksi)
                    homeListener!!.onSuccess()
                }
            } catch (e: ApiException) {
                homeListener!!.onFailure(ERROR_API)
                job.cancel()
            } catch (e: NoInternetException) {
                homeListener!!.onFailure(ERROR_INTERNET)
                job.cancel()
            } catch (e: SocketTimeoutException) {
                job.cancel()
            }

        }
        return data
    }

    fun getLoggedInAdvertiser() = clientRepository.getClient()
}