package com.genossys.pasangbalihoclient.ui.transaksi.detailTransaksi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.Advertiser
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository
import com.genossys.pasangbalihoclient.data.db.response.DetailTransaksiResponse
import com.genossys.pasangbalihoclient.utils.*
import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class DetailTransaksiViewModel(
    private val repository: TransaksiRepository,
    private val advertiserRepository: AdvertiserRepository
) : ViewModel() {

    var listener: CommonListener? = null
    var job: Job? = null

    suspend fun getUser(): MutableLiveData<Advertiser> = runBlocking {
        val advertiser = MutableLiveData<Advertiser>()
        Coroutines.io {
            try {
//                advertiser.postValue(advertiserRepository.getAdvertiser2())
                withContext(Dispatchers.Main) {

                }
            } catch (e: NullPointerException) {
                job?.cancel()
            }
        }
        return@runBlocking advertiser
    }



    suspend fun getDetailTransaksi(
        idTransaksi: Int
    ): MutableLiveData<DetailTransaksiResponse> {
        job = Job()
        val transaksi = MutableLiveData<DetailTransaksiResponse>()
        listener?.onStartJob()

        job = Coroutines.io {
            try {
                val transaksiResponse = repository.getDetailTransaksi(idTransaksi)
                transaksiResponse.let {
                    transaksi.postValue(it)
                    withContext(Dispatchers.Main) {
                        listener?.onSuccessJob()
                    }
                }

            } catch (e: ApiException) {
                listener?.onFailure(ERROR_API)
                job?.cancel()
            } catch (e: NoInternetException) {
                listener?.onFailure(ERROR_INTERNET)
                job?.cancel()
            } catch (e: SocketTimeoutException) {
                listener?.onTimeOut("soket timeout")
                job?.cancel()
            }

        }
        return transaksi
    }
}