package com.genossys.pasangbalihoclient.ui.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.PageNotifikasi
import com.genossys.pasangbalihoclient.data.db.repository.NotifRepository
import com.genossys.pasangbalihoclient.utils.ApiException
import com.genossys.pasangbalihoclient.utils.CommonListener
import com.genossys.pasangbalihoclient.utils.Coroutines
import com.genossys.pasangbalihoclient.utils.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class NotificationsViewModel(
    private val notifRepository: NotifRepository
) : ViewModel() {

    var commonListener: CommonListener? = null
    lateinit var job: Job

    suspend fun getDataNotif(page: Int, idClient: Int, awal: Boolean): MutableLiveData<PageNotifikasi> {

        if (awal) {
            commonListener?.onStarted()
        } else {
            commonListener?.onStartJob()
        }
        val notif = MutableLiveData<PageNotifikasi>()
        job = Job()
        job = Coroutines.io {
            try {
                val notifResponse = notifRepository.getDataNotif(page, idClient)
                notifResponse.data.let {
                    notif.postValue(it)
                    withContext(Dispatchers.Main) {
                        if (it.notif.isEmpty()) {
                            withContext(Dispatchers.Main) {
                                commonListener?.onEmpty()
                            }
                        } else {
                            withContext(Dispatchers.Main) {
                                commonListener?.onSuccess()
                            }
                        }
                    }
                }
            } catch (e: ApiException) {
                commonListener?.onFailure(e.message!!)
                job.cancel()
            } catch (e: NoInternetException) {
                commonListener?.onFailure(e.message!!)
                job.cancel()
            } catch (e: SocketTimeoutException) {
                commonListener?.onTimeOut("Timeout")
                job.cancel()
            }

        }
        return notif
    }
}