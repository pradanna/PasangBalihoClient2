package com.genossys.pasangbalihoclient.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.response.DetailBalihoResponse
import com.genossys.pasangbalihoclient.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class DetailBalihoViewModel(
    private val repository: BalihoRepository
) : ViewModel() {

    var detailListener: DetailListener? = null
    lateinit var job: Job


    fun getDetailBaliho(id: Int): MutableLiveData<DetailBalihoResponse> {
        job = Job()
        val detail = MutableLiveData<DetailBalihoResponse>()
        detailListener?.onStarted()
        job = Coroutines.io {
            try {
                detail.postValue(repository.getDetail(id))
                withContext(Dispatchers.Main) {
                    detailListener?.onDetailLoaded()
                }

            } catch (e: ApiException) {
                detailListener?.onFailure(ERROR_API)
            } catch (e: NoInternetException) {
                detailListener?.onFailure(ERROR_INTERNET)
            } catch (e: SocketTimeoutException) {
                detailListener?.onTimeOut(ERROR_INTERNET)
                job.cancel()
            }

        }
        return detail
    }


}