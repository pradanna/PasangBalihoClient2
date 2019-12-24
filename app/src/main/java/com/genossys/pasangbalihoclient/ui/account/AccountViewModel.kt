package com.genossys.pasangbalihoclient.ui.account

import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.repository.ClientRepository
import com.genossys.pasangbalihoclient.utils.*
import java.net.SocketTimeoutException

class AccountViewModel(
    val repository: ClientRepository
) : ViewModel() {

    var accountListener: AccountListener? = null
//    val user = repository.getAdvertiser()

//    fun getLoggedInAdvertiser() = repository.getAdvertiser()

    suspend fun signOut(fcmToken: String) {
        accountListener?.onStartedSignOut()

        Coroutines.io {
            try {
                repository.deleteFcmClient(fcmToken).let {
                    if (it.respon == "success") {
                        repository.deleteClient()
                        accountListener?.onSuccessSignOut()
                    } else {
                        accountListener?.onFailureSignOut(ERROR_API)
                    }
                }
            } catch (e: ApiException) {
                accountListener?.onFailureSignOut(ERROR_API)
            } catch (e: NoInternetException) {
                accountListener?.onFailureSignOut(ERROR_INTERNET)
            } catch (e: SocketTimeoutException) {
                accountListener?.onFailureSignOut("terjadi kesalahan pada koneksi")
            }
        }
    }

}