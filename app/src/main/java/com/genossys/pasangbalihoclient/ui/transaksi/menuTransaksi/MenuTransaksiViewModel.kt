package com.genossys.pasangbalihoclient.ui.transaksi.menuTransaksi

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.PageTransaksi
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository
import com.genossys.pasangbalihoclient.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class MenuTransaksiViewModel(
    private val repository: TransaksiRepository
) : ViewModel() {

    var listener: CommonListener? = null
    var job: Job? = null

    suspend fun getDataTransaksi(
        idClient: Int,
        status: String,
        page: Int
    ): MutableLiveData<PageTransaksi> {
        job = Job()
        val transaksi = MutableLiveData<PageTransaksi>()
        listener?.onStartJob()

        job = Coroutines.io {
            try {
                val transaksiResponse =
                    repository.getDataTransaksi(idClient, status, page)
                transaksiResponse.transaksi.let {
                    transaksi.postValue(it)
                    if (it.transaksi.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            listener?.onEmpty()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            listener?.onSuccessJob()
                        }
                    }
                }
            } catch (e: ApiException) {
                listener?.onFailure(ERROR_API)
            } catch (e: NoInternetException) {
                listener?.onFailure(ERROR_INTERNET)
            } catch (e: SocketTimeoutException) {
                listener?.onTimeOut("soket timeout, ulang job lagi")
                job?.cancel()
            }

        }
        return transaksi
    }
}