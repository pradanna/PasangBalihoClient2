package com.genossys.pasangbalihoclient.ui.baliho

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genossys.pasangbalihoclient.data.db.entity.Kategori
import com.genossys.pasangbalihoclient.data.db.entity.Kota
import com.genossys.pasangbalihoclient.data.db.entity.PageBaliho
import com.genossys.pasangbalihoclient.data.db.repository.BalihoRepository
import com.genossys.pasangbalihoclient.data.db.repository.KategoriRepository
import com.genossys.pasangbalihoclient.data.db.repository.KotaRepository
import com.genossys.pasangbalihoclient.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class BalihoViewModel(
    val balihoRepository: BalihoRepository,
    val kotaRepository: KotaRepository,
    val kategoriRepository: KategoriRepository
) : ViewModel() {

    var listener: SimpleListener? = null
    lateinit var job: Job

    suspend fun getBalihoClient(
        idClient: Int,
        kota: String,
        kategori: String,
        sortby: String,
        urutan: String,
        tambahan: String,
        page: Int
    ): MutableLiveData<PageBaliho> {
        job = Job()
        val baliho = MutableLiveData<PageBaliho>()
            listener?.onStarted()

        job = Coroutines.io {
            try {
                val balihosResponse =
                    balihoRepository.getBalihoClient(
                        idClient,
                        kota,
                        kategori,
                        sortby,
                        urutan,
                        tambahan,
                        page
                    )
                balihosResponse.baliho.let {
                    baliho.postValue(it)
                    if (it.baliho.isEmpty()) {
                        withContext(Dispatchers.Main) {
                            listener?.onEmpty()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            listener?.onSuccess()
                        }
                    }
                }
            } catch (e: ApiException) {
                listener?.onFailure(ERROR_API)
                job.cancel()
            } catch (e: NoInternetException) {
                listener?.onFailure(ERROR_INTERNET)
                job.cancel()
            } catch (e: SocketTimeoutException) {
                listener?.onTimeOut(ERROR_INTERNET)
                job.cancel()
            }

        }
        return baliho
    }

    suspend fun getKota(): MutableLiveData<List<Kota>> {
        val kota = MutableLiveData<List<Kota>>()
//        pencarianListener?.onStarted()
        Coroutines.io {
            try {
                val kotaResponse = kotaRepository.getDataAllKota()
                kotaResponse.kota.let {
                    kota.postValue(it)
                    withContext(Dispatchers.Main) {
                        //                        pencarianListener?.onSuccess()
                    }
                }
            } catch (e: ApiException) {
//                pencarianListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
//                pencarianListener?.onFailure(e.message!!)
            }catch (e: SocketTimeoutException){

            }

        }
        return kota
    }

    suspend fun getKategori(): MutableLiveData<List<Kategori>> {
        val kategori = MutableLiveData<List<Kategori>>()
//        pencarianListener?.onStarted()
        Coroutines.io {
            try {
                val kategoriResponse = kategoriRepository.getDataAllKategori()
                kategoriResponse.kategori.let {
                    kategori.postValue(it)
                    withContext(Dispatchers.Main) {
                        //                        pencarianListener?.onSuccess()
                    }
                }
            } catch (e: ApiException) {
//                pencarianListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
//                pencarianListener?.onFailure(e.message!!)
            }catch (e: SocketTimeoutException){

            }

        }
        return kategori
    }
}