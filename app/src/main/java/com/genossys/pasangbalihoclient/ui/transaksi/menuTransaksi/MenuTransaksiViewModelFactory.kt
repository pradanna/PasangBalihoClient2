package com.genossys.pasangbalihoclient.ui.transaksi.menuTransaksi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.genossys.pasangbalihoclient.data.db.repository.TransaksiRepository

class MenuTransaksiViewModelFactory(
    private val repository: TransaksiRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuTransaksiViewModel(repository) as T
    }
}