package com.genossys.pasangbalihoclient.ui.transaksi.detailTransaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.AdvertiserRepository
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository

class DetailTransaksiViewModelFactory(
    private val repository: TransaksiRepository,
    private val advertiserRepository: AdvertiserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailTransaksiViewModel(repository, advertiserRepository) as T
    }
}